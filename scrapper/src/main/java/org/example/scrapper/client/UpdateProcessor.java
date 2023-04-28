package org.example.scrapper.client;

import org.example.scrapper.dto.requests.LinkUpdateRequest;

public interface UpdateProcessor {
    void update(LinkUpdateRequest request);
}
