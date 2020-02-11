package com.assignment.cardinalhealth.model

sealed class PhotosAdapterEvent(val feed: Feed) {
    /* Describes photos click event  */
    class ClickEvent(feed: Feed) : PhotosAdapterEvent(feed)
}