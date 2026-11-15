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
                            .lastName("Dark Vader")
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
                            .store(storeRepository.findById(1L).get())
                            .build()
            );



/////////////// Productos de Sushi de Sofia

            productRepository.save(
                    Product.builder()
                            .name("Nigiri de Salmón")
                            .price(BigDecimal.valueOf(25100))
                            .description("Finas láminas de salmón fresco sobre arroz avinagrado.")
                            .imageURL("/assets/productImgs/2/1.jpg")
                            .status(EProductStatus.AVAILABLE)
                            .store(storeRepository.findById(2L).get())
                            .build()
            );

            productRepository.save(
                    Product.builder()
                            .name("Roll Philadelphia")
                            .price(BigDecimal.valueOf(23500))
                            .description("Relleno de salmón fresco, queso crema y palta, cubierto con sésamo.")
                            .imageURL("/assets/productImgs/2/2.jpg")
                            .status(EProductStatus.AVAILABLE)
                            .store(storeRepository.findById(2L).get())
                            .build()
            );

            productRepository.save(
                    Product.builder()
                            .name("Roll New York")
                            .price(BigDecimal.valueOf(22800))
                            .description("Salmón rosado, palta fresca y pepino envueltos en arroz y sésamo.")
                            .imageURL("/assets/productImgs/2/3.jpg")
                            .status(EProductStatus.AVAILABLE)
                            .store(storeRepository.findById(2L).get())
                            .build()
            );

            productRepository.save(
                    Product.builder()
                            .name("Roll California")
                            .price(BigDecimal.valueOf(20500))
                            .description("Kanikama, palta y pepino con semillas de sésamo tostado por fuera.")
                            .imageURL("/assets/productImgs/2/4.jpg")
                            .status(EProductStatus.AVAILABLE)
                            .store(storeRepository.findById(2L).get())
                            .build()
            );

            productRepository.save(
                    Product.builder()
                            .name("Hot Roll de Salmón")
                            .price(BigDecimal.valueOf(24900))
                            .description("Roll rebozado en panko crocante, relleno de salmón y queso crema.")
                            .imageURL("/assets/productImgs/2/5.jpg")
                            .status(EProductStatus.AVAILABLE)
                            .store(storeRepository.findById(2L).get())
                            .build()
            );

            productRepository.save(
                    Product.builder()
                            .name("Sashimi de Salmón")
                            .price(BigDecimal.valueOf(27500))
                            .description("Cortes frescos de salmón rosado premium servidos sin arroz.")
                            .imageURL("/assets/productImgs/2/6.jpg")
                            .status(EProductStatus.AVAILABLE)
                            .store(storeRepository.findById(2L).get())
                            .build()
            );

            productRepository.save(
                    Product.builder()
                            .name("Roll Buenos Aires")
                            .price(BigDecimal.valueOf(26200))
                            .description("Relleno de salmón, langostinos y queso, cubierto con láminas de salmón.")
                            .imageURL("/assets/productImgs/2/7.jpg")
                            .status(EProductStatus.AVAILABLE)
                            .store(storeRepository.findById(2L).get())
                            .build()
            );

            productRepository.save(
                    Product.builder()
                            .name("Geishas de Salmón")
                            .price(BigDecimal.valueOf(25800))
                            .description("Bocados de queso crema y palta envueltos en láminas de salmón fresco.")
                            .imageURL("/assets/productImgs/2/8.jpg")
                            .status(EProductStatus.AVAILABLE)
                            .store(storeRepository.findById(2L).get())
                            .build()
            );

            productRepository.save(
                    Product.builder()
                            .name("Roll Acevichado")
                            .price(BigDecimal.valueOf(26500))
                            .description("Langostinos empanizados y palta, bañado en salsa acevichada cítrica.")
                            .imageURL("/assets/productImgs/2/9.jpg")
                            .status(EProductStatus.AVAILABLE)
                            .store(storeRepository.findById(2L).get())
                            .build()
            );

            productRepository.save(
                    Product.builder()
                            .name("Temaki de Langostinos")
                            .price(BigDecimal.valueOf(18900))
                            .description("Cono de alga nori relleno de arroz, langostinos furai, palta y queso.")
                            .imageURL("/assets/productImgs/2/10.jpg")
                            .status(EProductStatus.AVAILABLE)
                            .store(storeRepository.findById(2L).get())
                            .build()
            );

            productRepository.save(
                    Product.builder()
                            .name("Roll Vegano")
                            .price(BigDecimal.valueOf(19500))
                            .description("Palta, pepino, zanahoria glaseada y tomates secos con sésamo.")
                            .imageURL("/assets/productImgs/2/11.jpg")
                            .status(EProductStatus.AVAILABLE)
                            .store(storeRepository.findById(2L).get())
                            .build()
            );

            productRepository.save(
                    Product.builder()
                            .name("Combinado 30 Piezas")
                            .price(BigDecimal.valueOf(48000))
                            .description("Selección variada de rolls, nigiris y geishas de salmón fresco.")
                            .imageURL("/assets/productImgs/2/12.jpg")
                            .status(EProductStatus.AVAILABLE)
                            .store(storeRepository.findById(2L).get())
                            .build()
            );





/*

            List<Product> pizzaProducts = productRepository.findAll()
                    .stream()
                    .filter(product -> product.getStore().getId().equals(1L))
                    .limit(3)
                    .toList();

            User consumer = userRepository.findByEmail("juanSky@mail.com").get();

            User rider = userRepository.findByEmail("ana@mail.com").get();

            Store pizzaStore = storeRepository.findById(1L).get();

            orderRepository.save(
                    Order.builder()
                            .consumer(consumer)
                            .rider(null)
                            .store(pizzaStore)
                            .products(List.of(
                                    pizzaProducts.get(0),
                                    pizzaProducts.get(1)
                            ))
                            .customerAddress("Av Colon 1234")
                            .customerLatitude(BigDecimal.valueOf(-37.9970000))
                            .customerLongitude(BigDecimal.valueOf(-57.5500000))
                            .longitude(pizzaStore.getLongitude())
                            .latitude(pizzaStore.getLatitude())
                            .total(
                                    pizzaProducts.get(0).getPrice()
                                            .add(pizzaProducts.get(1).getPrice())
                            )
                            .status(EOrderStatus.PENDING)
                            .paymentType(EPaymentType.EFECTIVO)
                            .build()
            );

            orderRepository.save(
                    Order.builder()
                            .consumer(consumer)
                            .rider(rider)
                            .store(pizzaStore)
                            .products(List.of(
                                    pizzaProducts.get(1),
                                    pizzaProducts.get(2)
                            ))
                            .customerAddress("Av Independencia 4321")
                            .customerLatitude(BigDecimal.valueOf(-37.9911111))
                            .customerLongitude(BigDecimal.valueOf(-57.5611111))
                            .longitude(pizzaStore.getLongitude())
                            .latitude(pizzaStore.getLatitude())
                            .total(
                                    pizzaProducts.get(1).getPrice()
                                            .add(pizzaProducts.get(2).getPrice())
                            )
                            .status(EOrderStatus.CONFIRMED)
                            .paymentType(EPaymentType.TRANSFERENCIA)
                            .build()
            );

            orderRepository.save(
                    Order.builder()
                            .consumer(consumer)
                            .rider(rider)
                            .store(pizzaStore)
                            .products(List.of(
                                    pizzaProducts.get(0)
                            ))
                            .customerAddress("Calle Falsa 123")
                            .longitude(pizzaStore.getLongitude())
                            .latitude(pizzaStore.getLatitude())
                            .customerLatitude(BigDecimal.valueOf(-37.9855555))
                            .customerLongitude(BigDecimal.valueOf(-57.5755555))
                            .total(
                                    pizzaProducts.get(0).getPrice()
                            )
                            .status(EOrderStatus.COMPLETED)
                            .paymentType(EPaymentType.EFECTIVO)
                            .build()
            );

            orderRepository.save(
                    Order.builder()
                            .consumer(consumer)
                            .rider(null)
                            .store(pizzaStore)
                            .products(List.of(
                                    pizzaProducts.get(2)
                            ))
                            .customerAddress("Diagonal Pueyrredon 555")
                            .longitude(pizzaStore.getLongitude())
                            .latitude(pizzaStore.getLatitude())
                            .customerLatitude(BigDecimal.valueOf(-37.9888888))
                            .customerLongitude(BigDecimal.valueOf(-57.5666666))
                            .total(
                                    pizzaProducts.get(2).getPrice()
                            )
                            .status(EOrderStatus.CANCELLED)
                            .paymentType(EPaymentType.TRANSFERENCIA)
                            .build()
            );

            productRepository.save(
                    Product.builder()
                            .name("Napolitana")
                            .price(BigDecimal.valueOf(10.5))
                            .description("Pizza Napo con todo")
                            .imageURL("/pizzaNapolitana")
                            .status(EProductStatus.AVAILABLE)
                            .store(storeRepository.findById(1L).get())
                            .build()
            );

            productRepository.save(
                    Product.builder()
                            .name("Calabrese")
                            .price(BigDecimal.valueOf(14.0))
                            .description("Pizza Calabria con todo")
                            .imageURL("/pizzaCalabrese")
                            .status(EProductStatus.AVAILABLE)
                            .store(storeRepository.findById(1L).get())
                            .build()
            );

            storeRepository.save(
                    Store.builder()
                            .name("Lomitos Hut de Monica")
                            .address("Calle 20")
                            .owner(userRepository.findByEmail("monicaazul@mail.com").get())
                            .latitude(BigDecimal.valueOf(-37.982457540007395))
                            .longitude(BigDecimal.valueOf(-57.56733097751799))
                            .build()
            );




            productRepository.save(
                    Product.builder()
                            .name("Lomo completo")
                            .price(BigDecimal.valueOf(12.5))
                            .description("Lomo complete con todo")
                            .imageURL("/lomoCompleto")
                            .status(EProductStatus.AVAILABLE)
                            .store(storeRepository.findById(2L).get())
                            .build()
            );

            productRepository.save(
                    Product.builder()
                            .name("Lomo basico")
                            .price(BigDecimal.valueOf(5.5))
                            .description("Lomo solo")
                            .imageURL("/lomo basico")
                            .status(EProductStatus.AVAILABLE)
                            .store(storeRepository.findById(2L).get())
                            .build()
            );
*/

            System.out.println("Datos de prueba cargados");
        }


    }
}
