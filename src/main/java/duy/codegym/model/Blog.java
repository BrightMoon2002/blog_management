package duy.codegym.model;

import net.bytebuddy.implementation.bind.annotation.Empty;
import org.hibernate.validator.constraints.Length;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDate;

@Entity
@Table(name = "blogs")
public class Blog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "error.name.blank")
    @Length(min = 5, max = 50)
    private String title;

    @NotEmpty(message = "{error.name.blank}")
    @Length(min = 2, max = 2000)
    private String content;
    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;
    private LocalDate datePost;
    public Blog() {
    }

    public Blog(Long id, String title, String content) {
        this.id = id;
        this.title = title;
        this.content = content;
    }

    public Blog(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public Blog(Long id, String title, String content, Category category) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.category = category;
    }

    public Blog(Long id, String title, String content, LocalDate datePost) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.datePost = datePost;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public LocalDate getDatePost() {
        return datePost;
    }

    public void setDatePost(LocalDate datePost) {
        this.datePost = datePost;
    }
// nếu dùng custom validation thì không dùng cái dưới và không phải implement interface validator nhưng phải cài bean trong appConfig
//    @Override
//    public boolean supports(Class<?> clazz) {
//        return false;
//    }
//
//    @Override
//    public void validate(Object target, Errors errors) {
//        Blog blog = (Blog) target;
//        String tittle = blog.getTitle();
//
//        if (tittle == null || "".equals(tittle)) {
//            errors.rejectValue("title", "error.name.blank", "cant be left blank");
//        }
//    }
}
