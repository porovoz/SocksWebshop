package com.bestapp.sockswebshop.services;

import com.bestapp.sockswebshop.model.Color;
import com.bestapp.sockswebshop.model.Size;
import com.bestapp.sockswebshop.model.Socks;

public interface SocksService {

    Socks addSocks(Socks socks, long quantity);

    Socks editSocks(Socks socks, long quantity);

    long getSocksNumByParam(Color color, Size size, int cottonMin, int cottonMax);

    boolean deleteSocks(Socks socks, long quantity);
}
