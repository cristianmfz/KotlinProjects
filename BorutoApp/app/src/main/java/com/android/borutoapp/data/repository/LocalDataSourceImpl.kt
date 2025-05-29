package com.android.borutoapp.data.repository

import com.android.borutoapp.data.local.BorutoDatabase
import com.android.borutoapp.domain.model.Hero
import com.android.borutoapp.domain.repository.LocalDataSource

class LocalDataSourceImpl(borutoDatabase: BorutoDatabase): LocalDataSource {

    private val heroDao = borutoDatabase.heroDao()

    override suspend fun getSelectedHero(heroId: Int): Hero {
        return heroDao.getSelectedHero(heroId = heroId)
    }
}