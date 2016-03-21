package com.example;

import com.vaadin.annotations.Theme;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.Grid;
import com.vaadin.ui.UI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Collection;
import java.util.stream.Stream;

@SpringBootApplication
public class ReservationServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ReservationServiceApplication.class, args);
	}

	@Bean
	CommandLineRunner runner(ReservationRepository rr) {
		return args -> Stream.of(
				"Julia", "Mia", "Phil", "Dave", "Pieter",
				"Bridget", "Stephane", "Josh", "Jennifer")
				.forEach(n -> rr.save(new Reservation(n)));
	}
}

@RepositoryRestResource
interface ReservationRepository  extends JpaRepository<Reservation, Long> {
	@RestResource(path = "by-name", rel = "by-name")
	Collection<Reservation> findByReservationName(@Param("rn") String rn);
}

@Controller
class ReservationMvcController {
	private final ReservationRepository reservationRepository;

	@Autowired
	public ReservationMvcController(ReservationRepository rr) {
		this.reservationRepository = rr;
	}

	@RequestMapping(method = RequestMethod.GET, value = "/reservations.mvc")
	public String renderReservations(Model model) {
		model.addAttribute("reservations", this.reservationRepository.findAll());
		// find template named 'reservations'
		return "reservations";
	}

}

@SpringUI(path = "ui")
@Theme("valo")
class ReservationUI extends UI {
	private final ReservationRepository reservationRepository;

	@Autowired
	public ReservationUI(ReservationRepository reservationRepository) {
		this.reservationRepository = reservationRepository;
	}

	@Override
	protected void init(VaadinRequest vaadinRequest) {
		Grid table = new Grid();
		BeanItemContainer<Reservation> container = new BeanItemContainer<>(
				Reservation.class,
				this.reservationRepository.findAll()
		);
		table.setContainerDataSource(container);
		table.setSizeFull();
		setContent(table);
	}
}

























