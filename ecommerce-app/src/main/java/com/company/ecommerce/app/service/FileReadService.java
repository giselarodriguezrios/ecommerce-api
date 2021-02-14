package com.company.ecommerce.app.service;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class FileReadService {

    @Autowired
    public FileReadService() {}

    public List<List<String>> readFile(Resource resource) throws IOException, CsvValidationException {
        List<List<String>> records = new ArrayList<>();
        CSVReader csvReader = new CSVReader(new FileReader(resource.getFile()));
        String[] values = null;
        while ((values = csvReader.readNext()) != null) {
            records.add(Arrays.asList(values));
        }
        return records;
    }
}
