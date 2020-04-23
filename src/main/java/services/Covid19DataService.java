package services;

import model.VirusData;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.IOException;
import java.io.StringReader;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

@Controller
public class Covid19DataService {

    private List<VirusData> virusAllDataList = new ArrayList<>();

    public List<VirusData> getVirusAllDataList() {
        return virusAllDataList;
    }

    @GetMapping("/")
    public String index(Model model){
        List<VirusData> virusConfirmedCases = virusAllDataList;
        int totalConfirmedCases = virusConfirmedCases.stream().mapToInt(totalCases -> Integer.parseInt(totalCases.getTotalCases())).sum();
        model.addAttribute("virusDataList", this.virusAllDataList);
        model.addAttribute("virusConfirmedCases", totalConfirmedCases);
        return "index";
    }

    @Scheduled(fixedRate = 100000)
    public void fetchCovid19Data() throws IOException, InterruptedException {
        String covid19DataSource = "https://raw.githubusercontent.com/CSSEGISandData/COVID-19/master/csse_covid_19_data/csse_covid_19_time_series/time_series_covid19_confirmed_global.csv";
        HttpClient httpClient = HttpClient.newHttpClient();
        HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(URI.create(covid19DataSource))
                .build();
        HttpResponse httpResponse = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
        System.out.println("Got the Source Data");
        List<VirusData> virusDataList = new ArrayList();
        StringReader stringReader = new StringReader(httpResponse.body().toString());
        Iterable<CSVRecord> records = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(stringReader);
        for (CSVRecord record : records) {
            VirusData virusData = new VirusData();
            virusData.setState(record.get("Province/State"));
            virusData.setCountry(record.get("Country/Region"));
            virusData.setTotalCases(record.get(record.size()-1));
            virusDataList.add(virusData);
        }
        virusAllDataList = virusDataList;
        System.out.println(getVirusAllDataList());
    }

}
