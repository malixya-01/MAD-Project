package com.example.supportapp.DataClasses

data class User(var name: String? = null,
                var email: String? = null,
                var phone: String? = null,
                var address: String? = null,
                var uid: String? = null,
                var isVerified : Boolean? = null
)
