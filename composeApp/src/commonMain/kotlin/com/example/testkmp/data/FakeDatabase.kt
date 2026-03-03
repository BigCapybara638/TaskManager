package com.example.testkmp.data

import com.example.testkmp.domain.models.Categories
import com.example.testkmp.domain.models.Task

class FakeDatabase {
    val list = listOf(
        Task("dddd", "", "", ""),
        Task("gtgddd", "", "", ""),
        Task("dddsd", "", "", ""),
        Task("ddytyfdd", "", "", ""),
        Task("dddds", "", "", ""),
        Task("dddd", "", "", ""),
        Task("dddd", "", "", ""),
        Task("dddd", "", "", ""),
        Task("dddsd", "", "", ""),
        Task("ddytyfdd", "", "", ""),
        Task("dddds", "", "", ""),
        Task("dddd", "", "", ""),
        Task("dddd", "", "", ""),
        Task("dddd", "", "", ""),
        Task("dddsd", "", "", ""),
        Task("ddytyfdd", "", "", ""),
        Task("dddds", "", "", ""),
        Task("dddd", "", "", ""),
        Task("dddd", "", "", ""),
        Task("dddd", "", "", ""),
        )

    val categories = listOf(
        Categories("0", "Домашние дела", "Описание"),
        Categories("0", "В колледже", "Описание"),
        Categories("0", "На тренировке", "Описание"),
        Categories("0", "На работе", "Описание"),
        Categories("0", "Другое", "Описание"),
        Categories("0", "Другое", "Описание"),
        Categories("0", "Другое", "Описание"),
        Categories("0", "Другое", "Описание"),
    )

    val listTaskInCat = listOf(
        Task("ddytyfdd", "ddytyfdd", "", ""),
        Task("dddds", "dddds", "", ""),
        Task("dddd", "dddd", "", ""),
        Task("dddd", "влылвыллвы", "", ""),
        Task("dddd", "dddd", "", ""),
    )
}