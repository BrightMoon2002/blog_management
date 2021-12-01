package duy.codegym.service.blog;

import duy.codegym.model.Blog;
import duy.codegym.model.Category;
import duy.codegym.service.IService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IBlogService extends IService<Blog> {
    List<Blog> findAllByCategory(Category category);
    Page<Blog> findAllOrderByCategory(Pageable pageable);
}
