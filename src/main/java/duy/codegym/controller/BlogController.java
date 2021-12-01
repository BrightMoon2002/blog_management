package duy.codegym.controller;

import com.sun.org.apache.xpath.internal.operations.Mod;
import duy.codegym.model.Blog;
import duy.codegym.model.Category;
import duy.codegym.service.blog.IBlogService;
import duy.codegym.service.category.ICategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Controller
@PropertySource("classpath:upload_file.properties")
@RequestMapping("/blogs")
public class BlogController {
    @Autowired
    private IBlogService blogService;
    @Autowired
    private ICategoryService categoryService;
    @Value("${file-upload}")
    private String fileUpload;

    @ModelAttribute("categories")
    public Iterable<Category> categories(){
        return categoryService.findAll();
    }
    @GetMapping("/**")
    public ModelAndView showList(@PageableDefault(value = 3)Pageable pageable){

        ModelAndView model = new ModelAndView("/blog/list");
        model.addObject("blogList", blogService.findAllOrderByCategory(pageable));
        return model;
    }
    @GetMapping("/create")
    public ModelAndView showCreateoForm(){
        ModelAndView model = new ModelAndView("/blog/create");
        model.addObject("blog", new Blog());
        return model;
    }

    @PostMapping("/create")
    public ModelAndView createNewBlog(@ModelAttribute("blog") Blog blog){
        ModelAndView model = new ModelAndView("/blog/create");
        blog.setDatePost(LocalDate.now());
        blogService.save(blog);
        model.addObject("blog", new Blog());
        model.addObject("message", "Congrat! you just create new blog successful. Go to HomePage to see it");
        return model;
    }

    @GetMapping("/delete")
    public ModelAndView showDeleteForm(@RequestParam("id") Blog blog) {
        ModelAndView model = new ModelAndView("/blog/delete");
        model.addObject("blog", blog);
        return model;
    }

    @PostMapping("/delete")
    public ModelAndView deleteBlog(@ModelAttribute("blog") Blog blog) {
        ModelAndView model = new ModelAndView("/blog/delete");
        blogService.remove(blog);
        model.addObject("blog", new Blog());
        model.addObject("message", "delete this blog successful!");
        return model;
    }

    @GetMapping("/edit")
    public ModelAndView showEditForm(@RequestParam("id") Blog blog) {
        ModelAndView model = new ModelAndView("/blog/edit");
        model.addObject("blog", blog);
        return model;
    }

    @PostMapping("/edit")
    public ModelAndView editBlog(@ModelAttribute("blog") Blog blog) {
        ModelAndView model = new ModelAndView("/blog/edit");
        model.addObject("blog", blog);
        model.addObject("message", "Edit blog successful, go to homepage to see your blog!");
        blogService.save(blog);
        return model;
    }

    @GetMapping("/sort")
    public ModelAndView sortByCategory(@PageableDefault(value = 3) Pageable pageable) {
        ModelAndView model = new ModelAndView("/blog/list");
        long count = 1;
        for (Category c: categories()
             ) {
            if (c.getId() > count) {
                count = c.getId();
            }
        }
        List<Blog> blogList = new ArrayList<>();
        for (long i = 1; i <= count; i++) {
           List<Blog> blogs = (List<Blog>) blogService.findAllByCategory(categoryService.findById(i).get());
           if (blogs!= null) {
               for (int j = 0; j < blogs.size(); j++) {
                   blogList.add(blogs.get(j));
               }
           }
        }
        Page<Blog> blogPage = (Page<Blog>) blogList;
        model.addObject("blogList", blogList);
        return model;
    }

}
