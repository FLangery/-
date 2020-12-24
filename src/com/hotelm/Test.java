package com.hotelm;

import com.hotelm.dao.ClientDao;
import com.hotelm.eneity.Client;

public class Test {
    public static void main(String[] args) {
        ClientDao dao = new ClientDao();
        Client client = new Client();
        client.setName("lis");
        client.setCard("23");
        client.setAge(18);
        client.setSex("nan");
        System.out.println(dao.addClient(client));
    }
}
