package pl.merdala.hexademo.mongo;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import pl.merdala.hexademo.domain.service.StockPositionsRepository;

@Repository
public interface ReactiveMongoStockPositionRepository extends StockPositionsRepository, ReactiveMongoRepository<StockPositionDocument, ObjectId> {
}
