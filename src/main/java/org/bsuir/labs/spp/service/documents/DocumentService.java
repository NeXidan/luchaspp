package org.bsuir.labs.spp.service.documents;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

public interface DocumentService<T>{
    void generateCsv(List<T> data, HttpServletResponse response);

    void generatePdf(List<T> data, boolean lock, HttpServletResponse response);

    void generateXls(List<T> data, HttpServletResponse response);
}
