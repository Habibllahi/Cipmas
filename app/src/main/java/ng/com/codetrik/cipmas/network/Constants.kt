package ng.com.codetrik.cipmas.network

const val BASE_URL : String = "https://cipmas.herokuapp.com/api/v1/"
const val SELECTED_SUPPLIER_ID: String = "ng.com.codetrik.cipmas.SupplierRecyclerAdapter.SELECTED_SUPPLIER_ID"
const val SELECTED_SUPPLIER_NAME: String = "ng.com.codetrik.cipmas.SupplierRecyclerAdapter.SELECTED_SUPPLIER_NAME"
const val MATERIAL_ACTIVITY_BUNDLE: String = "ng.com.codetrik.cipmas.MaterialAdd.MATERIAL_ACTIVITY_BUNDLE"
const val SUPPLIER_RECYCLER_BUNDLE_EXTRA: String = "ng.com.codetrik.cipmas.SupplierRecyclerAdapter.SUPPLIER_RECYCLER_BUNDLE_EXTRA"
const val ADD_CUSTOMER_FROM_QUOTATION_ACTIVITY_INTENT_FLAG = "ng.com.codetrik.cipmas.QuotationAdd.ADD_CUSTOMER_FROM_QUOTATION_ACTIVITY_INTENT_FLAG"
const val CUSTOMER_ADD_ACTIVITY_INTENT_FLAG = "ng.com.codetrik.cipmas.CustomerAdd.CUSTOMER_ADD_ACTIVITY_INTENT_FLAG"
const val MATERIAL_ADD_ACTIVITY_INTENT_FLAG = "ng.com.codetrik.MaterialAdd.MATERIAL_ADD_ACTIVITY_INTENT_FLAG"
const val SUPPLIER_ADD_ACTIVITY_INTENT_FLAG = "ng.com.codetrik.SupplierAdd.SUPPLIER_ADD_ACTIVITY_INTENT_FLAG"
const val QUOTATION_ADD_ACTIVITY_INTENT_FLAG = "ng.com.codetrik.QuotationAdd.QUOTATION_ADD_ACTIVITY_INTENT_FLAG"
const val SELECTED_CUSTOMER_ID = "ng.com.codetrik.CustomerRecyclerAdapter.SELECTED_CUSTOMER_ID"
const val SELECTED_CUSTOMER_NAME = "ng.com.codetrik.CustomerRecyclerAdapter.SELECTED_CUSTOMER_NAME"
val RETROFIT_CIPMAS_API_HANDLER : CipmasAPI =  RetrofitClientInstance.getRetrofitInstance().create(CipmasAPI::class.java)