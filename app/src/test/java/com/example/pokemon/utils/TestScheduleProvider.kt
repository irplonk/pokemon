package com.example.pokemon.utils

import io.reactivex.Scheduler
import io.reactivex.schedulers.Schedulers

class TestScheduleProvider : BaseSchedulerProvider {
    override fun computation(): Scheduler = Schedulers.trampoline()

    override fun io(): Scheduler = Schedulers.trampoline()

    override fun ui(): Scheduler = Schedulers.trampoline()
}