package guice;
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

import com.google.inject.persist.PersistService;

import javax.inject.Inject;
import javax.inject.Singleton;
//CHECKSTYLE:OFF
@Singleton
public class JpaInitializer {

    @Inject
    public JpaInitializer (PersistService persistService) {
        persistService.start();
    }

}
//CHECKSTYLE:ON