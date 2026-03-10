
namespace bonus_certs.dal {

    public interface IRepo<TEntity, TKey> : IDisposable where TEntity: class {
        int Add(TEntity entity);
        int Update(TEntity entity);
        int Delete(TEntity entity);
        TEntity Get(TKey id);
        IEnumerable<TEntity> GetAll();
    }
}