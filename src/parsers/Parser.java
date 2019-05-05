package parsers;

import java.io.InputStream;
import java.util.List;

public interface Parser {
    List parse(InputStream in,String charsetName);
}
