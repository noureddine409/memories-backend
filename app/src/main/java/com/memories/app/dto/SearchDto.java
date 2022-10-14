package com.memories.app.dto;





import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.data.domain.Sort;

import com.memories.app.commun.CoreConstant.Pagination;




public class SearchDto implements Serializable {
	
	private static final long serialVersionUID = 697346882356264965L;
	private Integer page;
    private Integer size;
    private List<SortDto> sort;
    private List<Filter> filters;
    
    public List<SortDto> getSort() {
		return sort;
	}

	public List<Filter> getFilters() {
		return filters;
	}

	public Integer getPage() {
        return (Objects.isNull(page) ? Pagination.DEFAULT_PAGE_NUMBER : page);
    }

    public Integer getSize() {
        return (Objects.isNull(size) ? Pagination.DEFAULT_PAGE_SIZE : size);
    }

    public Sort generateSort() {
        List<Sort.Order> orders;

        if (Objects.isNull(sort) || sort.isEmpty())
            orders = List.of(new Sort.Order(Pagination.DEFAULT_SORT_DIRECTION, Pagination.DEFAULT_SORT_PROPERTY));
        else
            orders = this.sort.stream()
                    .map(sortDto -> new Sort.Order(sortDto.getDirection(), sortDto.getProperty()))
                    .collect(Collectors.toList());

        return Sort.by(orders);
    }
    /*
    public void validate() throws BadRequestException {
        if (Objects.nonNull(page))
            if (this.page < 0)
                throw new BadRequestException(null, new BadRequestException(), Validation.PAGINATION_PAGE_NUMBER, null);

        if (Objects.nonNull(size)) {
            if (this.size <= 0)
                throw new BadRequestException(null, new BadRequestException(), Validation.PAGINATION_PAGE_SIZE_MIN, null);

            if (this.size > Pagination.MAX_PAGE_SIZE)
                throw new BadRequestException(null, new BadRequestException(), Exception.PAGINATION_PAGE_SIZE_MAX, new Object[]{MAX_PAGE_SIZE});
        }
    }*/
    
}
