package api.dto.get;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Positive;

public class Pagination {

    @Min(value = 0, message = "Cannot be less than 0")
    private int page;
    @Positive(message = "Cannot be 0 or negative")
    private int size;

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

}
