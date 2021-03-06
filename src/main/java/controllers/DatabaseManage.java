package controllers;
/*-
 * #%L
 * Winery
 * %%
 * Copyright (C) 2019 Faculty of Informatics, University of Debrecen
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-3.0.html>.
 * #L%
 */


import com.google.inject.Guice;
import com.google.inject.Injector;
import guice.PersistenceModule;
import models.BottledDao;
import models.ContainersDao;
import models.Containers;
//CHECKSTYLE:OFF
class DatabaseManage {

    DatabaseManage() {
    }

    public Containers handleNewRequest(String AmString,String ContString,String Type,String WineId){
        int contInt = Integer.parseInt(ContString);
        int AmInt = Integer.parseInt(AmString);

        Containers containers = Containers.builder()
                .Container(contInt)
                .Amount(AmInt)
                .Type(Type)
                .WineId(WineId)
                .build();
        return containers;
    }

    public boolean isNotNumeric(String str) {
        try {
            Double.parseDouble(str);
            return false;
        } catch(NumberFormatException e){
            return true;
        }
    }

    public boolean checkbottling(String Choice,String Amount){
        if (isNotNumeric(Amount)) return false;
        if (Choice==null) return false;
        return true;


    }


    private Injector injector = Guice.createInjector(new PersistenceModule("jpa-persistence-unit-1"));

    private BottledDao bottledDao = injector.getInstance(BottledDao.class);
    private ContainersDao containersDao = injector.getInstance(ContainersDao.class);



    public BottledDao getBottledDao() {return bottledDao; }

    public ContainersDao getContainersDao() {
        return containersDao;
    }


//CHECKSTYLE:ON


}
