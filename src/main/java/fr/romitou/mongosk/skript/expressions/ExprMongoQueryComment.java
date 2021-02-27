package fr.romitou.mongosk.skript.expressions;

import ch.njol.skript.classes.Changer;
import ch.njol.skript.expressions.base.SimplePropertyExpression;
import ch.njol.util.coll.CollectionUtils;
import fr.romitou.mongosk.elements.MongoSKQuery;
import org.bukkit.event.Event;

import javax.annotation.Nonnull;

public class ExprMongoQueryComment extends SimplePropertyExpression<MongoSKQuery, String> {

    static {
        register(
            ExprMongoQueryComment.class,
            String.class,
            "mongo[(db|sk)] comment",
            "mongoskqueries"
        );
    }

    @Nonnull
    @Override
    public String convert(MongoSKQuery query) {
        return query.getComment();
    }

    @Override
    public Class<?>[] acceptChange(Changer.ChangeMode mode) {
        switch (mode) {
            case SET:
            case DELETE:
                return CollectionUtils.array(String.class);
            default:
                return new Class[0];
        }
    }

    @Override
    public void change(@Nonnull Event e, Object[] delta, @Nonnull Changer.ChangeMode mode) {
        MongoSKQuery mongoSKQuery = getExpr().getSingle(e);
        if (mongoSKQuery == null || delta == null)
            return;
        if (!(delta[0] instanceof String))
            return;
        switch (mode) {
            case SET:
                mongoSKQuery.setComment((String) delta[0]);
                break;
            case DELETE:
                mongoSKQuery.setComment(null);
                break;
        }
    }

    @Nonnull
    @Override
    public Class<? extends String> getReturnType() {
        return String.class;
    }

    @Nonnull
    @Override
    protected String getPropertyName() {
        return "mongo comment";
    }
}
