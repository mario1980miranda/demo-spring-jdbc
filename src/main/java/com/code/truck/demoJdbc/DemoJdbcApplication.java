package com.code.truck.demoJdbc;

import lombok.*;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.relational.core.conversion.MutableAggregateChange;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.event.AfterConvertCallback;
import org.springframework.data.relational.core.mapping.event.BeforeConvertCallback;
import org.springframework.data.relational.core.mapping.event.BeforeSaveCallback;
import org.springframework.data.repository.CrudRepository;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@SpringBootApplication
public class DemoJdbcApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoJdbcApplication.class, args);
	}

	@Bean
	ApplicationRunner demoRunner(CityRepository repository) {
		return args -> {
			City city = City.builder()
					.name("Quebec")
					.build();
			repository.save(city);
			repository.findAll().forEach(System.out::println);
		};
	}

}

@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
@ToString
@Builder(toBuilder = true)
class City {
	@Id @Column("CITY_CODE") Integer id;
	@Column("CITY_NAME") String name;
	@Transient String temporaryData;
}

@Component
class CityBeforeSaveCallback implements BeforeSaveCallback<City>{
	@Override
	public City onBeforeSave(City aggregate, MutableAggregateChange<City> aggregateChange) {
		return aggregate;
	}
}

@Component
class CityBeforeConvertCallback implements BeforeConvertCallback<City> {
	@Override
	public City onBeforeConvert(City aggregate) {
		return aggregate;
	}
}

@Component
class CityAfterConvertCallback implements AfterConvertCallback<City>{
	@Override
	public City onAfterConvert(City aggregate) {
		return aggregate;
	}
}

/*class CiryRowMapper implements RowMapper<City>{
	@Override
	public City mapRow(ResultSet rs, int rowNum) throws SQLException {

		return null;
	}
}*/

/*@Component
class CityRowMapper implements RowMapper<City> {
	@Override
	public City mapRow(ResultSet rs, int rowNum) throws SQLException {
		City cityToMap = new City();
		cityToMap.setId(rs.getInt("CITY_CODE"));
		cityToMap.setName(rs.getString("CITY_NAME"));
		return cityToMap;
	}
}*/

interface CityRepository extends CrudRepository<City, Integer> {}
