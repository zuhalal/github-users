package com.example.githubusers

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import kotlin.collections.ArrayList

val mapper = jacksonObjectMapper()

object UserData {
    val listData: List<User>
        get() {
            val list: ArrayList<User> = mapper.readValue(jsonString, object: TypeReference<ArrayList<User>>() {})
            return list
        }
}

val jsonString = """
[
  {
    "username": "zuhalal",
    "name": "Zuhal Alimul Hadi",
    "city": "Jakarta",
    "avatar": "https://avatars.githubusercontent.com/u/74417769?s=400&u=ff5ce788008677f656448025dfbe45ec08e6b8a8&v=4",
    "follower": 5,
    "following": 6,
    "company": "Dicoding satu",
    "location": "Jakarta",
    "repository": "https://github.com/zuhalal"
  },
  {
    "username": "vbalden0",
    "name": "Vina",
    "city": "Boyu",
    "avatar": "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTGiheJJLnIF6lOVnUwXLoiGkL0J0SIMe6Ce-nt8dcMWEvn6jNE_61PgAucxd-U2aOykEI&usqp=CAU",
    "follower": 1,
    "following": 1,
    "company": "Browsebug",
    "location": "Jakarta",
    "repository": "https://github.com/a"
  },
  {
    "username": "pkinneir1",
    "name": "Pancho",
    "city": "Huangzhou",
    "avatar": "https://raw.githubusercontent.com/Ashwinvalento/cartoon-avatar/master/lib/images/male/86.png",
    "follower": 2,
    "following": 2,
    "company": "Fivebridge",
    "location": "Surabaya",
    "repository": "https://github.com/b"
  },
  {
    "username": "cgreenshiels2",
    "name": "Camel",
    "city": "Duwakbuter",
    "avatar": "https://raw.githubusercontent.com/Ashwinvalento/cartoon-avatar/master/lib/images/female/10.png",
    "follower": 3,
    "following": 3,
    "company": "Rooxo",
    "location": "Bandung",
    "repository": "https://github.com/c"
  },
  {
    "username": "itweedlie3",
    "name": "Illa",
    "city": "Xinli",
    "avatar": "https://raw.githubusercontent.com/Ashwinvalento/cartoon-avatar/master/lib/images/male/45.png",
    "follower": 4,
    "following": 4,
    "company": "JumpXS",
    "location": "Ambon",
    "repository": "https://github.com/d"
  },
  {
    "username": "sdevaen4",
    "name": "Stephannie",
    "city": "Tân Kỳ",
    "avatar": "https://raw.githubusercontent.com/Ashwinvalento/cartoon-avatar/master/lib/images/female/68.png",
    "follower": 5,
    "following": 5,
    "company": "Skinix",
    "location": "Jakarta",
    "repository": "https://github.com/e"
  },
  {
    "username": "bsawday5",
    "name": "Bessie",
    "city": "Danirai",
    "avatar": "https://raw.githubusercontent.com/Ashwinvalento/cartoon-avatar/master/lib/images/male/5.png",
    "follower": 6,
    "following": 6,
    "company": "Shufflebeat",
    "location": "Jakarta",
    "repository": "https://github.com/e"
  },
  {
    "username": "dwimpey7",
    "name": "Diandra",
    "city": "Itajubá",
    "avatar": "https://raw.githubusercontent.com/Ashwinvalento/cartoon-avatar/master/lib/images/female/5.png",
    "follower": 8,
    "following": 8,
    "company": "Fatz",
    "location": "Surabaya",
    "repository": "https://github.com/f"
  },
  {
    "username": "brodmell8",
    "name": "Burnard",
    "city": "Cool űrhajó",
    "avatar": "https://raw.githubusercontent.com/Ashwinvalento/cartoon-avatar/master/lib/images/male/5.png",
    "follower": 9,
    "following": 9,
    "company": "Mudo",
    "location": "Surabaya",
    "repository": "https://github.com/ab"
  },
  {
    "username": "lrobarts9",
    "name": "Loria",
    "city": "Thị Trấn Cao Lộc",
    "avatar": "https://raw.githubusercontent.com/Ashwinvalento/cartoon-avatar/master/lib/images/male/45.png",
    "follower": 10,
    "following": 10,
    "company": "Feedfire",
    "location": "Jakarta",
    "repository": "https://github.com/abc"
  }
]
""".trimIndent()