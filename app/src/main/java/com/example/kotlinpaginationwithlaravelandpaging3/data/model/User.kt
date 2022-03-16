package com.example.kotlinpaginationwithlaravelandpaging3.data.model

data class UserData(
    val id: Int = -1,
    val name: String,
    val address : String,
    val phone: String,
    val email: String
)
//UserResponse for the json
data class UserResponse(
    val next_page_url : String? = null,
    val prev_page_url : String? = null,
    val data: List<UserData> = listOf(),
    val total: Int = -1
)

/*
 ***** JSON from Laravel 8 api returns a List [] with a sublist called data which containts the users
[
    {
        "current_page": 1,
        "data": [
            {
                "id": 1,
                "name": "Gia Hahn",
                "email": "patricia.wyman@example.org",
                "email_verified_at": "2022-03-16T12:40:35.000000Z",
                "phone": "+19205771138",
                "address": "7389 Kemmer Trafficway Apt. 131\nWest Coltontown, ME 08931",
                "created_at": "2022-03-16T12:40:35.000000Z",
                "updated_at": "2022-03-16T12:40:35.000000Z"
            },
            {
                "id": 2,
                "name": "Dr. Rubie Yundt V",
                "email": "rveum@example.com",
                "email_verified_at": "2022-03-16T12:40:35.000000Z",
                "phone": "+17074308459",
                "address": "87445 Xander Trafficway Suite 893\nNorth Vitochester, WV 63449-9294",
                "created_at": "2022-03-16T12:40:35.000000Z",
                "updated_at": "2022-03-16T12:40:35.000000Z"
            }
        ],
        "first_page_url": "http://192.168.0.28:8000/api/users?page=1",
        "next_page_url": "http://192.168.0.28:8000/api/users?page=2",
        "total": 100
    }
]
* */
