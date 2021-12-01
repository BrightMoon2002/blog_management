package duy.codegym.formatter;

import duy.codegym.model.Blog;
import duy.codegym.service.blog.IBlogService;
import org.springframework.format.Formatter;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.util.Locale;
import java.util.Optional;

@Component
public class BlogFormatter implements Formatter<Optional<Blog>> {
    private IBlogService blogService;

    public BlogFormatter(IBlogService blogService) {
        this.blogService = blogService;
    }


    @Override
    public Optional<Blog> parse(String id, Locale locale) throws ParseException {
        return blogService.findById(Long.parseLong(id));
    }

    @Override
    public String print(Optional<Blog> object, Locale locale) {
        return "";
    }
}
