<!-- Category Modal -->
<div class="modal fade" id="categoryModal" tabindex="-1" aria-labelledby="categoryModalLabel" aria-hidden="true">
  <div class="modal-dialog">
    <form onsubmit="submitCategoryForm(event)" class="modal-content">
      <div class="modal-header">
        <h5 class="modal-title" id="categoryModalLabel">Category Form</h5>
        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
      </div>
      <div class="modal-body">
        <input type="hidden" name="id" id="categoryId">

        <div class="mb-3">
          <label for="categoryName" class="form-label">Category Name</label>
          <select class="form-select" id="categoryName" name="name" >
            <option value="">-- Select Category --</option>
            <option value="Hygiene">Hygiene</option>
            <option value="Medicine">Medicine</option>
            <option value="Food">Food</option>
            <option value="Beverages">Beverages</option>
          </select>
        </div>

        <div class="mb-3">
          <label for="categoryDescription" class="form-label">Description</label>
          <textarea class="form-control" id="categoryDescription" name="description" rows="3" ></textarea>
        </div>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cancel</button>
        <button type="submit" class="btn btn-success">Save Category</button>
      </div>
    </form>
  </div>
</div>
