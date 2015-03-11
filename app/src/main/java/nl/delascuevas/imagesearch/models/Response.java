package nl.delascuevas.imagesearch.models;

import java.util.List;


/**
 * Created by juanma on 12/02/15.
 */

public class Response {

    public Integer responseStatus;
    public Results responseData;

    public class Results {
        public List<Item> results;
    }

    public class Item {
        public String tbUrl;
        public String url;

    }


}
