package pl.merdala.hexademo.mongo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import pl.merdala.hexademo.domain.service.StockPosition;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class StockPositionDocument extends StockPosition {
    @Id
    private ObjectId id;
}
