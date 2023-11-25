package com.bestapp.coursework03sockswebshop.services.impl;

import com.bestapp.coursework03sockswebshop.model.Color;
import com.bestapp.coursework03sockswebshop.model.Size;
import com.bestapp.coursework03sockswebshop.model.Socks;
import com.bestapp.coursework03sockswebshop.services.SocksFilesService;
import com.bestapp.coursework03sockswebshop.services.SocksService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.*;

@Service
public class SocksServiceImpl implements SocksService {

    private final SocksFilesService socksFilesService;

    private static Map<Socks, Long> socksMap = new HashMap<>();

    public SocksServiceImpl(SocksFilesService socksFilesService) {
        this.socksFilesService = socksFilesService;
    }

    @PostConstruct
    private void init() {
        try {
            readFromFile();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Socks addSocks(Socks socks, long quantity) {
        if (quantity > 0 && socks.getCottonPart() > 0 && socks.getCottonPart() <= 100) {
            socksMap.merge(socks, quantity, Long::sum);
            socksMap.putIfAbsent(socks, quantity);
            saveToFile();
        }
        return socks;
    }

    @Override
    public Socks editSocks(Socks socks, long quantity) {
        ObjectUtils.isNotEmpty(socksMap);
        if (quantity > 0 && socksMap.containsKey(socks)) {
            long number = socksMap.get(socks) - quantity;
            if (number >= 0) {
                socksMap.merge(socks, quantity, (a, b) -> a - b);
                socksMap.putIfAbsent(socks, quantity);
                saveToFile();
            } else {
                throw new UnsupportedOperationException("There are no socks in stock");
            }
        }
        return socks;
    }

    @Override
    public long getSocksNumByParam(Color color, Size size, int cottonMin, int cottonMax) {
        ObjectUtils.isNotEmpty(socksMap);
        long count = 0;
        if (cottonMin >= 0 && cottonMax >= 0 && cottonMax >= cottonMin) {
            for (Map.Entry<Socks, Long> entry : socksMap.entrySet()) {
                if (entry.getKey().getColor() == color && entry.getKey().getSize() == size &&
                entry.getKey().getCottonPart() >= cottonMin && entry.getKey().getCottonPart() <= cottonMax) {
                    count += entry.getValue();
                }
            }
        }
        return count;
    }

    @Override
    public boolean deleteSocks(Socks socks, long quantity) {
        ObjectUtils.isNotEmpty(socksMap);
        if (quantity > 0 && socksMap.containsKey(socks)) {
            long number = socksMap.get(socks) - quantity;
            if (number >= 0) {
                socksMap.merge(socks, quantity, (a, b) -> a - b);
                socksMap.putIfAbsent(socks, quantity);
                saveToFile();
                return true;
            } else {
                throw new UnsupportedOperationException("There are no socks in stock");
            }
        }
        return false;
    }

    private void saveToFile() {
        try {
            String json = new ObjectMapper().writeValueAsString(socksMap);
            socksFilesService.saveToFile(json);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    private void readFromFile() {
        try {
            String json = socksFilesService.readFromFile();
            socksMap = new ObjectMapper().readValue(json, new TypeReference<HashMap<Socks, Long>>() {
            });
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
