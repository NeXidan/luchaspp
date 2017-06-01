package org.bsuir.labs.spp.service.documents.factories;

import java.util.List;

public interface DocumentDataFactory<T> {
    public List<T> getData();
}
