package com.greencity.ui.locale;

/**
 * Keys aligned with GreenCityClient {@code src/assets/i18n/*.json}.
 */
public enum UiMessage {
    SIGN_UP("top-bar.sign-up"),

    EVENT_MORE("event.btn-top"),
    EVENT_JOIN("event.btn-join"),
    EVENT_EDIT("event.btn-edit"),
    EVENT_DETAILS_SAVE("homepage.events.btn.save-event"),
    EVENT_DETAILS_JOIN("homepage.events.btn.join-btn"),
    EVENT_DETAILS_EDIT("homepage.events.btn.edit"),
    EVENT_FILTER_TIME("homepage.events.time"),
    EVENT_FILTER_LOCATION("homepage.events.filter-location"),
    EVENT_FILTER_STATUS("homepage.events.filter-status"),
    EVENT_FILTER_TYPE("homepage.events.filter-type"),

    NEWS_TAG_NEWS("homepage.eco-news.tags.news"),
    NEWS_TAG_EVENTS("homepage.eco-news.tags.events"),
    NEWS_TAG_EDUCATION("homepage.eco-news.tags.education"),
    NEWS_TAG_INITIATIVES("homepage.eco-news.tags.initiatives"),
    NEWS_TAG_ADS("homepage.eco-news.tags.ads"),
    NEWS_SEARCH_PLACEHOLDER("search.search-popup.placeholder"),
    CREATE_NEWS_CANCEL("create-news.cancel-button"),
    CREATE_NEWS_EDIT("create-news.edit-button"),
    CREATE_NEWS_TAG_NEWS("create-news.tags.news"),
    CREATE_NEWS_TAG_EVENTS("create-news.tags.events"),
    CREATE_NEWS_TAG_EDUCATION("create-news.tags.education"),
    CREATE_NEWS_TAG_INITIATIVES("create-news.tags.initiatives"),
    CREATE_NEWS_TAG_ADS("create-news.tags.ads"),

    CREATE_EVENT_TITLE_LABEL("create-event.placeholder"),
    CREATE_EVENT_INVITE("create-event.invite"),
    CREATE_EVENT_PLACE("create-event.event-place"),
    CREATE_EVENT_ONLINE("create-event.event-online"),

    ABOUT_US_HEADER("about-us.intro.block-1.header"),
    ABOUT_US_VISION_HEADER("about-us.intro.block-2.header"),

    PLACES_FILTER_SHOPS("places.filter.shops"),
    PLACES_FILTER_RESTAURANTS("places.filter.restaurants"),
    PLACES_FILTER_RECYCLING_POINTS("places.filter.recycling-points"),
    PLACES_FILTER_EVENTS("places.filter.events"),
    PLACES_FILTER_SAVED_PLACES("places.filter.saved-places");

    private final String key;

    UiMessage(String key) {
        this.key = key;
    }

    public String key() {
        return key;
    }

    public String text() {
        return UiText.get(this);
    }
}
