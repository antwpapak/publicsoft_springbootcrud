<template>
  <el-dialog type="warning" title="Διαχείριση Προμηθευτή" :visible="visible" :modal="true"
             :close-on-click-modal="false"
             :close-on-press-escape="false" :modal-append-to-body="false" :show-close="false" width="75%"
             @close="clearValidation">

    <el-card>
      <el-form ref="supplierForm" :model="supplier" :rules="rules" label-position="top">

        <el-row :gutter="20">
          <!-- LEFT COLUMN - Basic Info-->
          <el-col :span="12">

            <el-form-item label="Εισάγετε το επωνυμία"
                          prop="companyName">
              <el-input prefix-icon="fa fa-user"
                        v-model="supplier.companyName"
                        placeholder="Επωνυμία"
                        autoComplete="name">
              </el-input>
            </el-form-item>

            <el-form-item label="Εισάγετε το όνομα"
                          prop="firstName">
              <el-input prefix-icon="fa fa-user"
                        v-model="supplier.firstName"
                        placeholder="Όνομα"
                        autoComplete="name">
              </el-input>
            </el-form-item>

            <el-form-item label="Εισάγετε το επώνυμο"
                          prop="lastName">
              <el-input prefix-icon="fa fa-user"
                        v-model="supplier.lastName"
                        placeholder="Επώνυμο"
                        autoComplete="name">
              </el-input>
            </el-form-item>

            <el-form-item label="Εισάγετε τον Α.Φ.Μ."
                          prop="vatNumber">
              <el-input prefix-icon="fa fa-envelope"
                        v-model="supplier.vatNumber"
                        placeholder="Α.Φ.Μ."
              >
              </el-input>
            </el-form-item>

            <el-form-item label="Εισάγετε τη Δ.Ο.Υ."
                          prop="irsOffice">
              <el-input prefix-icon="fa fa-envelope"
                        v-model="supplier.irsOffice"
                        placeholder="Δ.Ο.Υ."
              >
              </el-input>
            </el-form-item>

          </el-col>

          <!-- RIGHT COLUMN - Address Info -->
          <el-col :span="12">

            <el-form-item label="Εισάγετε τη διεύθυνση"
                          prop="address">
              <el-input prefix-icon="fa fa-envelope"
                        v-model="supplier.address"
                        placeholder="address"
              >
              </el-input>
            </el-form-item>

            <el-form-item label="Εισάγετε τον Τ.Κ."
                          prop="zipCode">
              <el-input prefix-icon="fa fa-envelope"
                        v-model="supplier.zipCode"
                        placeholder="T.K."
                        autoComplete="zip"
              >
              </el-input>
            </el-form-item>

            <el-form-item label="Εισάγετε την πόλη"
                          prop="city">
              <el-input prefix-icon="fa fa-envelope"
                        v-model="supplier.city"
                        placeholder="Πόλη"
                        autoComplete="city"
              >
              </el-input>
            </el-form-item>

            <el-form-item label="Εισάγετε τη χώρα"
                          prop="country">
              <el-input prefix-icon="fa fa-envelope"
                        v-model="supplier.country"
                        placeholder="Χώρα"
                        autoComplete="country"
              >
              </el-input>
            </el-form-item>

          </el-col>
        </el-row>

      </el-form>

      <el-row>
        <el-col style="text-align: right">
          <el-button type="success"
                     icon="fa fa-save"
                     @click="save"> Αποθήκευση </el-button>

          <el-button type="danger"
                     icon="fa fa-remove"
                     @click="confirmDelete"
                     :disabled="!isDeletable"> Διαγραφή </el-button>
        </el-col>
      </el-row>
    </el-card>

    <div slot="footer" class="dialog-footer card-footer">
      <el-button type="warning"
                 icon="el-icon-back"
                 @click="cancel"> Επιστροφή </el-button>
    </div>

  </el-dialog>
</template>
<script src="./SupplierVM.js"></script>
