package com.bestapp.coursework03sockswebshop.services;

import com.bestapp.coursework03sockswebshop.model.Color;
import com.bestapp.coursework03sockswebshop.model.Size;
import com.bestapp.coursework03sockswebshop.model.Socks;

public interface SocksService {

    Socks addSocks(Socks socks, long quantity);

    Socks editSocks(Socks socks, long quantity);

    long getSocksNumByParam(Color color, Size size, int cottonMin, int cottonMax);

    boolean deleteSocks(Socks socks, long quantity);
}
