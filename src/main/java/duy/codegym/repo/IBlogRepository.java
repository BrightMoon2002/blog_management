package duy.codegym.repo;

import duy.codegym.model.Blog;
import duy.codegym.model.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IBlogRepository extends PagingAndSortingRepository<Blog, Long> {
    List<Blog> findAllByCategory(@Param("category_id") Category categoryId);
    @Query(value = "SELECT * FROM blogs as b order by b.category_id asc, b.datePost asc", nativeQuery = true)
    Page<Blog> findAllOrderByCategory(Pageable pageable);
}
