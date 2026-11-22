package com.deliveryapp.backend.common.data;

import com.deliveryapp.backend.order.enums.EOrderStatus;
import com.deliveryapp.backend.order.enums.EPaymentType;
import com.deliveryapp.backend.order.model.Order;
import com.deliveryapp.backend.order.repository.OrderRepository;
import com.deliveryapp.backend.product.enums.EProductStatus;
import com.deliveryapp.backend.product.model.Product;
import com.deliveryapp.backend.product.repository.ProductRepository;
import com.deliveryapp.backend.store.model.Store;
import com.deliveryapp.backend.store.repository.StoreRepository;
import com.deliveryapp.backend.user.enums.ERole;
import com.deliveryapp.backend.user.model.User;
import com.deliveryapp.backend.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import net.datafaker.Faker;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import com.deliveryapp.backend.order.model.OrderItem;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Locale;
import java.util.stream.IntStream;

import static io.micrometer.common.util.StringUtils.truncate;

@Component
@RequiredArgsConstructor
public class DataSeeder implements CommandLineRunner {
    private final StoreRepository storeRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final PasswordEncoder passwordEncoder;
    private final OrderRepository orderRepository;

    @Override
    public void run(String... args) throws Exception {

        Faker faker = new Faker(new Locale("es"));

        userRepository.deleteAll();
        storeRepository.deleteAll();
        productRepository.deleteAll();
        orderRepository.deleteAll();

        if (userRepository.count() == 0) {
            userRepository.save(
                    User.builder()
                            .name("Lucas")
                            .lastName("Darth Vader")
                            .email("lucas@mail.com")
                            .password(passwordEncoder.encode("passDeLucas1"))
                            .role(ERole.ROLE_CONSUMER)
                            .build()
            );

            userRepository.save(
                    User.builder()
                            .name("Galo")
                            .lastName("Skywalker")
                            .email("galo@mail.com")
                            .password(passwordEncoder.encode("passDeGalo1"))
                            .role(ERole.ROLE_MERCHANT)
                            .build()
            );

            userRepository.save(
                    User.builder()
                            .name("Sofia")
                            .lastName("Leia")
                            .email("sofia@mail.com")
                            .password(passwordEncoder.encode("passDeSofia1"))
                            .role(ERole.ROLE_MERCHANT)
                            .build()
            );

            userRepository.save(
                    User.builder()
                            .name("Tomas")
                            .lastName("Yoda")
                            .email("tomas@mail.com")
                            .password(passwordEncoder.encode("passDeTomas1"))
                            .role(ERole.ROLE_RIDER)
                            .build()
            );

            userRepository.save(
                    User.builder()
                            .name("Nico")
                            .lastName("Chewbacca")
                            .email("nico@mail.com")
                            .password(passwordEncoder.encode("passDeNico1"))
                            .role(ERole.ROLE_ADMINISTRATOR)
                            .build()
            );



            storeRepository.save(
                    Store.builder()
                            .name("Galo Hut")
                            .address("Moreno 2902 - MDP")
                            .owner(userRepository.findByEmail("galo@mail.com").get())
                            .latitude(BigDecimal.valueOf(-37.994044345914496))
                            .longitude(BigDecimal.valueOf(-57.55520222953616))
                            .build()
            );

            storeRepository.save(
                    Store.builder()
                            .name("Sushi Zen")
                            .address("Las Heras 2625 - MDP")
                            .owner(userRepository.findByEmail("sofia@mail.com").get())
                            .latitude(BigDecimal.valueOf(-37.994044345914496))
                            .longitude(BigDecimal.valueOf(-57.55520222953616))
                            .build()
            );

/*    productRepository.save(
                    Product.builder()
                            .name("Pizza Napolitana")
                            .price(BigDecimal.valueOf(12500))
                            .description("Masa suave y esponjosa, bordes inflados, salsa de tomate fresca y mozzarella derretida con albahaca" )
                            .imageURL("/assets/productImgs/1/1.jpg")
                            .status(EProductStatus.AVAILABLE)
                            .store(storeRepository.findById(1L).get())
                            .build()

            );
*/
////////////// Productos de la Pizzeria de Galo
            productRepository.save(
                    Product.builder()
                            .name("Pizza Muzzarella")
                            .price(BigDecimal.valueOf(11000))
                            .description("Clásica salsa de tomate casera, abundante muzzarella derretida y orégano.")
                            .imageURL("/assets/productImgs/1/1.jpg")
                            .status(EProductStatus.AVAILABLE)
                            .stock(8)
                            .store(storeRepository.findById(1L).get())
                            .build()
            );

            productRepository.save(
                    Product.builder()
                            .name("Pizza Napolitana")
                            .price(BigDecimal.valueOf(12500))
                            .description("Salsa de tomate, muzzarella, rodajas de tomate fresco, ajo y albahaca.")
                            .imageURL("/assets/productImgs/1/2.jpg")
                            .status(EProductStatus.AVAILABLE)
                            .stock(5)
                            .store(storeRepository.findById(1L).get())
                            .build()
            );

            productRepository.save(
                    Product.builder()
                            .name("Pizza Fugazzeta")
                            .price(BigDecimal.valueOf(12000))
                            .description("Base crocante con abundante cebolla blanca caramelizada, muzzarella y orégano.")
                            .imageURL("/assets/productImgs/1/3.jpg")
                            .status(EProductStatus.AVAILABLE)
                            .stock(10)
                            .store(storeRepository.findById(1L).get())
                            .build()
            );

            productRepository.save(
                    Product.builder()
                            .name("Pizza Calabresa")
                            .price(BigDecimal.valueOf(13500))
                            .description("Salsa de tomate, muzzarella fundida y rodajas de longaniza calabresa.")
                            .imageURL("/assets/productImgs/1/4.jpg")
                            .status(EProductStatus.AVAILABLE)
                            .stock(3)
                            .store(storeRepository.findById(1L).get())
                            .build()
            );

            productRepository.save(
                    Product.builder()
                            .name("Pizza Jamón y Morrones")
                            .price(BigDecimal.valueOf(13200))
                            .description("Salsa de tomate, muzzarella, jamón cocido natural y morrones asados.")
                            .imageURL("/assets/productImgs/1/5.jpg")
                            .status(EProductStatus.AVAILABLE)
                            .stock(7)
                            .store(storeRepository.findById(1L).get())
                            .build()
            );

            productRepository.save(
                    Product.builder()
                            .name("Pizza Cuatro Quesos")
                            .price(BigDecimal.valueOf(14500))
                            .description("Combinación intensa de muzzarella, roquefort, parmesano y queso provolone.")
                            .imageURL("/assets/productImgs/1/6.jpg")
                            .status(EProductStatus.AVAILABLE)
                            .stock(0)
                            .store(storeRepository.findById(1L).get())
                            .build()
            );

            productRepository.save(
                    Product.builder()
                            .name("Pizza Rúcula y Crudo")
                            .price(BigDecimal.valueOf(15200))
                            .description("Muzzarella, jamón crudo estacionado, hojas de rúcula fresca y parmesano.")
                            .imageURL("/assets/productImgs/1/7.jpg")
                            .status(EProductStatus.AVAILABLE)
                            .stock(4)
                            .store(storeRepository.findById(1L).get())
                            .build()
            );

            productRepository.save(
                    Product.builder()
                            .name("Pizza Fugazzeta Rellena")
                            .price(BigDecimal.valueOf(16000))
                            .description("Doble masa rellena de muzzarella y jamón, cubierta con cebolla dorada.")
                            .imageURL("/assets/productImgs/1/8.jpg")
                            .status(EProductStatus.AVAILABLE)
                            .stock(2)
                            .store(storeRepository.findById(1L).get())
                            .build()
            );

            productRepository.save(
                    Product.builder()
                            .name("Pizza Pepperoni")
                            .price(BigDecimal.valueOf(14000))
                            .description("Salsa de tomate, muzzarella y rodajas de pepperoni ligeramente picante.")
                            .imageURL("/assets/productImgs/1/9.jpg")
                            .status(EProductStatus.AVAILABLE)
                            .stock(9)
                            .store(storeRepository.findById(1L).get())
                            .build()
            );

            productRepository.save(
                    Product.builder()
                            .name("Pizza Provolone")
                            .price(BigDecimal.valueOf(13800))
                            .description("Salsa de tomate, muzzarella y queso provolone gratinado con chimichurri.")
                            .imageURL("/assets/productImgs/1/10.jpg")
                            .status(EProductStatus.AVAILABLE)
                            .stock(6)
                            .store(storeRepository.findById(1L).get())
                            .build()
            );

            productRepository.save(
                    Product.builder()
                            .name("Pizza Anchoas")
                            .price(BigDecimal.valueOf(12800))
                            .description("Salsa de tomate especiada, filetes de anchoas, alcaparras y aceitunas negras.")
                            .imageURL("/assets/productImgs/1/11.jpg")
                            .status(EProductStatus.AVAILABLE)
                            .stock(1)
                            .store(storeRepository.findById(1L).get())
                            .build()
            );

            productRepository.save(
                    Product.builder()
                            .name("Pizza Champiñones")
                            .price(BigDecimal.valueOf(14200))
                            .description("Salsa de tomate, muzzarella, champiñones salteados al ajillo y perejil.")
                            .imageURL("/assets/productImgs/1/12.jpg")
                            .status(EProductStatus.AVAILABLE)
                            .stock(8)
                            .store(storeRepository.findById(1L).get())
                            .build()
            );

            productRepository.save(
                    Product.builder()
                            .name("Pizza Roquefort y Apio")
                            .price(BigDecimal.valueOf(14400))
                            .description("Base de muzzarella con queso azul fundido, apio fresco picado y nueces.")
                            .imageURL("/assets/productImgs/1/13.jpg")
                            .status(EProductStatus.AVAILABLE)
                            .stock(5)
                            .store(storeRepository.findById(1L).get())
                            .build()
            );

            productRepository.save(
                    Product.builder()
                            .name("Pizza Palmitos y Salsa Golf")
                            .price(BigDecimal.valueOf(14800))
                            .description("Muzzarella, jamón cocido, palmitos en rodajas, huevo duro y salsa golf.")
                            .imageURL("/assets/productImgs/1/14.jpg")
                            .status(EProductStatus.AVAILABLE)
                            .stock(3)
                            .store(storeRepository.findById(1L).get())
                            .build()
            );

            productRepository.save(
                    Product.builder()
                            .name("Pizza Vegetariana")
                            .price(BigDecimal.valueOf(13400))
                            .description("Salsa de tomate, muzzarella, berenjenas, zucchini, cebolla y morrones.")
                            .imageURL("/assets/productImgs/1/15.jpg")
                            .status(EProductStatus.AVAILABLE)
                            .stock(7)
                            .store(storeRepository.findById(1L).get())
                            .build()
            );

            System.out.println("Datos de prueba cargados");
        }


    }
}
