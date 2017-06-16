package com.wonlake.core;

import org.luaj.vm2.Globals;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.jse.JsePlatform;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

/**
 * Created by Administrator on 2017/5/12.
 */
@Service
public class GameClient {
    public static Logger logger = LoggerFactory.getLogger(GameClient.class);

    @PostConstruct
    public void start()
    {
        Globals globals = JsePlatform.standardGlobals();
        LuaValue chunk = globals.loadfile("lua/main.lua");
        chunk.call();
    }
}
