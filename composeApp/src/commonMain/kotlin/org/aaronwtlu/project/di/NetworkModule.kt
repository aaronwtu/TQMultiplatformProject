
package org.aaronwtlu.project.di

import org.aaronwtlu.project.NetworkServiceIMP
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val networkModule = module {
    singleOf(::NetworkServiceIMP)
}