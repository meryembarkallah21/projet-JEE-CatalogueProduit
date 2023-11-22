package com.poly.gestioncatalogue5gr1.controleur;

import com.poly.gestioncatalogue5gr1.dao.CategorieRepository;
import com.poly.gestioncatalogue5gr1.entities.Produit;
import com.poly.gestioncatalogue5gr1.service.IServiceProduit;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@AllArgsConstructor
public class ProduitControleur {

    private IServiceProduit serviceProduct;
    private CategorieRepository categorieRepository;

    @GetMapping("/index")
    public String getAllProducts(Model m,@RequestParam(name = "page",defaultValue ="1" ) int page,
                                 @RequestParam(name ="size",defaultValue ="2") int size,
                                 @RequestParam(name ="mc",defaultValue ="") String mc)
    {
        //List<Produit> liste=serviceProduit.getAllProducts();
        Page<Produit> listePage= serviceProduct.getProductsByMC(mc, PageRequest.of(page-1,size));
        m.addAttribute("produits", listePage.getContent());
        m.addAttribute("pages", new int[listePage.getTotalPages()]);
        m.addAttribute("current", listePage.getNumber());



        m.addAttribute("mc",mc);


        return "vue";
    }

    @GetMapping("/delete/{id}")
    public String deletePRoduct(@PathVariable("id") Long idProduit)
    {
        serviceProduct.deleteProduct(idProduit);
        return "redirect:/index";
    }

    @GetMapping("/formproduit")
    public String formAjout(Model m)
    {m.addAttribute("categories",categorieRepository.findAll());
        m.addAttribute("produit",new Produit());
        return "ajouterProduit";
    }

    @PostMapping("/save")
    public String saveProduct(@ModelAttribute Produit p)
    {
        serviceProduct.saveProduct(p);
        return "redirect:/index";
    }
    @GetMapping("/update/{id}")
    public String updateProduit(@PathVariable Long id,Model m)
    {
        Produit p= serviceProduct.getProduct(id);
        m.addAttribute("produit",p);
        m.addAttribute("categories",categorieRepository.findAll());
        return "ajouterProduit";
    }

}
