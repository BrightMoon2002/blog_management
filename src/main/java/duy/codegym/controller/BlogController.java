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
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
    public ModelAndView createNewBlog(@Valid @ModelAttribute("blog") Optional<Blog> blog, BindingResult bindingResult){
            if (blog.isPresent()){
                ModelAndView model = new ModelAndView("/blog/create");
                // cách dùng validate nếu dùng thì không cần anotation @Valid hoặc nếu dùng custom
                // valation thì cũng không cần dùng dòng dưới và cả anotation @Valid
//            new Blog().validate(blog.get(), bindingResult);
                if (!bindingResult.hasFieldErrors()) {
                    blog.get().setDatePost(LocalDate.now());
                    blogService.save(blog.get());
                    model.addObject("blog", new Blog());
                    model.addObject("message", "Congrat! you just create new blog successful. Go to HomePage to see it");
                }
                return model;
            } else {
                return new ModelAndView("/error-404");
            }

        }


    @GetMapping("/delete")
    public ModelAndView showDeleteForm(@RequestParam("id") Long id) {
            Optional<Blog> blog = blogService.findById(id);
            if (blog.isPresent()) {
                ModelAndView model = new ModelAndView("/blog/delete");
                model.addObject("blog", blog.get());
                return model;
            } else {
                return new ModelAndView("/error-404");
            }

    }

    @PostMapping("/delete")
    public ModelAndView deleteBlog(@ModelAttribute("blog") Optional<Blog> blog) {
        if (blog.isPresent()) {
            ModelAndView model = new ModelAndView("/blog/delete");
            blogService.remove(blog.get());
            model.addObject("blog", new Blog());
            model.addObject("message", "delete this blog successful!");
            return model;
        } else {
            return new ModelAndView("/error-404");
        }

    }

    @GetMapping("/edit")
    public ModelAndView showEditForm(@RequestParam("id") Optional<Blog> blog) {
            if (blog.isPresent()){
                ModelAndView model = new ModelAndView("/blog/edit");
                model.addObject("blog", blog.get());
                return model;
            } else {
                return new ModelAndView("/error-404");
            }

    }

    @PostMapping("/edit")
    public ModelAndView editBlog(@Valid @ModelAttribute("blog") Blog blog, BindingResult bindingResult) {
            ModelAndView model = new ModelAndView("/blog/edit");
            if (!bindingResult.hasFieldErrors()) {
                model.addObject("blog", blog);
                model.addObject("message", "Edit blog successful, go to homepage to see your blog!");
                blogService.save(blog);
            }
            return model;

    }

//    @GetMapping("/sort")
//    public ModelAndView sortByCategory(@PageableDefault(value = 3) Pageable pageable) {
//        ModelAndView model = new ModelAndView("/blog/list");
//        long count = 1;
//        for (Category c: categories()
//             ) {
//            if (c.getId() > count) {
//                count = c.getId();
//            }
//        }
//        List<Blog> blogList = new ArrayList<>();
//        for (long i = 1; i <= count; i++) {
//           List<Blog> blogs = (List<Blog>) blogService.findAllByCategory(categoryService.findById(i).get());
//           if (blogs!= null) {
//               for (int j = 0; j < blogs.size(); j++) {
//                   blogList.add(blogs.get(j));
//               }
//           }
//        }
//        Page<Blog> blogPage = (Page<Blog>) blogList;
//        model.addObject("blogList", blogList);
//        return model;
//    }

}
