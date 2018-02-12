package com.potato.jubilantpotato.dagger.components;

import com.potato.jubilantpotato.dagger.modules.ApplicationModule;
import com.potato.jubilantpotato.dagger.modules.api.ApiModule;
import dagger.Component;
import javax.inject.Singleton;

/**
 * Created by bry1337 on 25/01/2018.
 *
 * @author edwardbryan.abergas@gmail.com
 */

@Singleton @Component(modules = { ApplicationModule.class, ApiModule.class }) public interface ApplicationComponent {
}
