package com.multiplatform.kmpmovies

import com.multiplatform.kmpmovies.data.IosRegionDataSource
import com.multiplatform.kmpmovies.data.RegionDataSource
import com.multiplatform.kmpmovies.data.database.getDatabaseBuilder
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.bind
import org.koin.dsl.module

actual val nativeModule = module {
    single { getDatabaseBuilder() }
    factoryOf(::IosRegionDataSource) bind RegionDataSource::class
}