//import com.ainetwork.Application;
//import com.ainetwork.entity.Neo4jFriend;
//import com.ainetwork.entity.Neo4jOID;
//import com.ainetwork.mapper.Neo4jMapper;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.data.neo4j.core.Neo4jTemplate;
//import org.springframework.test.context.junit4.SpringRunner;
//
//import java.util.Optional;
//
///**
// * @Description:
// * @Author:fyc
// * @Date: 2024/11/23
// */
//@RunWith(SpringRunner.class)
//@SpringBootTest(classes = Application.class)
//public class Neo4jLocalTest {
//    @Autowired
//    private Neo4jTemplate neo4jTemplate;
//
//    @Test
//    public void testAddUser() {
//        Neo4jOID center = new Neo4jOID(1L, "1.2");
//        Neo4jOID left = new Neo4jOID(2L, "1.2.256");
//        Neo4jOID right = new Neo4jOID(3L, "1.2.256.115288");
//        Neo4jFriend leftFriend = new Neo4jFriend( right, 0);
//        Neo4jFriend rightFriend = new Neo4jFriend(left, 0);
//
//        center.getFriend()
//                .add(leftFriend);
//        center.getFriend()
//                .add(rightFriend);
//        neo4jTemplate.save(center);
//
//    }
//    @Autowired
//    Neo4jMapper neo4jMapper;
//    @Test
//    public void testFindOID(){
//
//        Optional<Neo4jOID> byId = neo4jMapper.findById(1L);
//        System.out.println(byId);
//
//    }
//}
