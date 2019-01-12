package com.msi.usermicroservice;

import com.thedeanda.lorem.Lorem;
import com.thedeanda.lorem.LoremIpsum;

public class Mock {
    private Lorem lorem;

    public Mock() {
        lorem = LoremIpsum.getInstance();
    }

    public void mock() {

        for (int i = 0; i < 100; i++) {
            
        }
    }
}
