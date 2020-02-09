package com.assignment.cardinalhealth.util

import com.assignment.cardinalhealth.model.Feed
import com.assignment.cardinalhealth.model.Media

object TestData {

    val expectedData  = Feed(
        title = "Tabby",
        dateTaken = "2020-02-07T17:49:49-08:00",
        link = "https://www.flickr.com/photos/tabbynera/49501530363/",
        tags = "cats",
        published="2020-02-07T16:52:14Z",
        media = Media(imageUrl = "https://live.staticflickr.com/65535/49501530363_1c2b83e35f_m.jpg")
    )
}