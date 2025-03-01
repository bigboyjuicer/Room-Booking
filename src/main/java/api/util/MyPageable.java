package api.util;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Sort;

public class MyPageable {

    private boolean last;
    private int totalElements;
    private int totalPages;
    private int size;
    private int number;
    private Sort sort;
    private int numberOfElements;
    private boolean first;
    private boolean empty;

    private MyPageable() {}

    public static MyPageable build(Page page) {
        MyPageable myPageable = new MyPageable();
        myPageable.last = page.isLast();
        myPageable.totalElements = page.getNumberOfElements();
        myPageable.totalPages = page.getTotalPages();
        myPageable.size = page.getSize();
        myPageable.number = page.getNumber();
        myPageable.sort = page.getSort();
        myPageable.numberOfElements = page.getNumberOfElements();
        myPageable.first = page.isFirst();
        myPageable.empty = page.isEmpty();
        return myPageable;
    }

    public boolean isLast() {
        return last;
    }

    public void setLast(boolean last) {
        this.last = last;
    }

    public int getTotalElements() {
        return totalElements;
    }

    public void setTotalElements(int totalElements) {
        this.totalElements = totalElements;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public Sort getSort() {
        return sort;
    }

    public void setSort(Sort sort) {
        this.sort = sort;
    }

    public int getNumberOfElements() {
        return numberOfElements;
    }

    public void setNumberOfElements(int numberOfElements) {
        this.numberOfElements = numberOfElements;
    }

    public boolean isFirst() {
        return first;
    }

    public void setFirst(boolean first) {
        this.first = first;
    }

    public boolean isEmpty() {
        return empty;
    }

    public void setEmpty(boolean empty) {
        this.empty = empty;
    }
}
