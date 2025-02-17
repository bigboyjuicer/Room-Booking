package api.dto.get;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Positive;

@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class Pagination {

    @Min(value = 1, message = "Cannot be less than 1")
    private int page;
    @Positive(message = "Cannot be 0 or negative")
    private int size;

    private int totalSize;

    public Pagination() {
    }

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

    public int getTotalSize() {
        return totalSize;
    }

    public void setTotalSize(int totalSize) {
        this.totalSize = totalSize;
    }
}
