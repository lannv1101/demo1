package edu.poly.colorshop.controller.guest;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import edu.poly.colorshop.entity.Product;
import edu.poly.colorshop.service.ProductService;

@Controller
@RequestMapping("color")
public class ProductController {
    @Autowired(required = true)
    ProductService productService;

    // tìm kiếm theo tên & phân trang
    @RequestMapping(value = "/product/list")
    public String list(ModelMap model, @RequestParam(name = "name", required = false) String name,
            @RequestParam("page") Optional<Integer> page, @RequestParam("size") Optional<Integer> size) {

        int currentPage = page.orElse(1);// trang hien tai
        int pageSize = size.orElse(12);// kich thuoc cua page
        Pageable pageable = PageRequest.of(currentPage - 1, pageSize, Sort.by("name"));
        Page<Product> resultPage = null;

        if (StringUtils.hasText(name)) {
            resultPage = productService.findByNameContaining(name, pageable);
            model.addAttribute("name", name);
        } else {
            resultPage = productService.findAll(pageable);
        }

        int totalPages = resultPage.getTotalPages();
        if (totalPages > 0) {
            int start = Math.max(1, currentPage - 2);
            int end = Math.min(currentPage + 2, totalPages);
            if (totalPages > 12) {
                if (end == totalPages)
                    start = end - 12;
                else if (start == 1)
                    end = start + 12;
            }
            List<Integer> pageNumbers = IntStream.rangeClosed(start, end).boxed().collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);

        }
        model.addAttribute("shop", "co");
        model.addAttribute("currentPage", currentPage);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("productPage", resultPage);
        return "site/product/list";

    }

    // detail product
    @RequestMapping(value = "/product/detail/{id}")
    public String detail(Model model, @PathVariable("id") Integer id) {
        Product item = productService.findById(id);
        model.addAttribute("item", item);
        return "site/product/single";
    }

    @GetMapping("/product/list/search/paginated")
    public String search(ModelMap model, @RequestParam(name = "name", required = false) String name,
            @RequestParam("page") Optional<Integer> page, @RequestParam("size") Optional<Integer> size,
            @RequestParam("cid") Optional<String> cid) {
        int currentPage = page.orElse(1);// trang hien tai
        int pageSize = size.orElse(12);// kich thuoc cua page
        Pageable pageable = PageRequest.of(currentPage - 1, pageSize, Sort.by("name"));
        Page<Product> resultPage = null;

        if (StringUtils.hasText(name)) {
            resultPage = productService.findByNameContaining(name, pageable);
            model.addAttribute("name", name);
        } else {
            // resultPage = productService.findAll(pageable);
            // Page<Product> list = productService.findByCategoryId(cid.get(), pageable);
            Page<Product> list = productService.findByCategoryId(cid.get(), pageable);
            resultPage = list;

        }

        int totalPages = resultPage.getTotalPages();
        if (totalPages > 0) {
            int start = Math.max(1, currentPage - 2);
            int end = Math.min(currentPage + 2, totalPages);
            if (totalPages > 12) {
                if (end == totalPages)
                    start = end - 12;
                else if (start == 1)
                    end = start + 12;
            }
            List<Integer> pageNumbers = IntStream.rangeClosed(start, end).boxed().collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);

        }
        model.addAttribute("list", "co");
        model.addAttribute("currentPage", currentPage);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("productPage", resultPage);
        return "site/product/list";

    }

}
