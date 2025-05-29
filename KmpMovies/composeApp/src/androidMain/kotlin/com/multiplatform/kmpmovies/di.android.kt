package com.multiplatform.kmpmovies

import android.location.Geocoder
import com.google.android.gms.location.LocationServices
import com.multiplatform.kmpmovies.data.AndroidRegionDataSource
import com.multiplatform.kmpmovies.data.RegionDataSource
import com.multiplatform.kmpmovies.data.database.getDatabaseBuilder
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.bind
import org.koin.dsl.module

actual val nativeModule = module {
    single { getDatabaseBuilder(get()) }
    factory { Geocoder(get()) }
    factory { LocationServices.getFusedLocationProviderClient(androidContext()) }
    factoryOf(::AndroidRegionDataSource) bind RegionDataSource::class
}