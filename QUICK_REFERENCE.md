# Quick Reference Guide - Hotel Reservation System Frontend

## 📁 Files Created

### Templates (14 files)
```
src/main/resources/templates/
├── index.html                  # Home page (public)
├── login.html                  # Login form
├── register.html               # Registration form
├── home.html                   # User dashboard
├── rooms.html                  # Browse rooms (th:each)
├── room-add.html               # Admin: Add room
├── room-edit.html              # Admin: Edit room
├── admin-rooms.html            # Admin: Manage rooms (table)
├── reservation-create.html     # Create reservation + price calculation
├── my-reservations.html        # User reservations (th:each)
├── payment-create.html         # Payment form + dynamic methods
├── my-payments.html            # Payment history (table, stats)
├── profile.html                # User profile
├── edit-profile.html           # Edit profile form
└── layout.html                 # Base layout (optional)
```

### CSS (1 file)
```
src/main/resources/static/css/
└── styles.css                  # 900+ lines, responsive, luxury design
```

## 🔗 Template → URL Mappings

### Public Routes
| Template | URL | Method | Purpose |
|----------|-----|--------|---------|
| index.html | GET `/` | GET | Home page |
| login.html | GET `/login` | GET | Show login |
| register.html | GET `/register` | GET | Show register |
| rooms.html | GET `/rooms` | GET | Browse rooms |

### User Routes
| Template | URL | Method | Model Attributes |
|----------|-----|--------|------------------|
| home.html | GET `/home` | GET | Session info |
| reservation-create.html | GET `/reservation/create/{id}` | GET | room, reservationCreateRequest |
| my-reservations.html | GET `/reservations` | GET | reservations |
| payment-create.html | GET `/payment/create/{id}` | GET | reservation, paymentCreateRequest |
| my-payments.html | GET `/payments` | GET | payments |
| profile.html | GET `/profile` | GET | user |
| edit-profile.html | GET `/profile/edit` | GET | userEditRequest |

### Admin Routes
| Template | URL | Method | Model Attributes |
|----------|-----|--------|------------------|
| room-add.html | GET `/admin/rooms/add` | GET | roomCreateRequest |
| room-edit.html | GET `/admin/rooms/{id}/edit` | GET | roomEditRequest, roomId |
| admin-rooms.html | GET `/admin/rooms` | GET | rooms |

## 📋 Model Attributes by Template

### login.html
```java
model.addAttribute("userLoginRequest", new UserLoginRequest());
model.addAttribute("errorMessage", "..."); // optional
```

### register.html
```java
model.addAttribute("userRegisterRequest", new UserRegisterRequest());
model.addAttribute("errorMessage", "..."); // optional
```

### rooms.html
```java
model.addAttribute("rooms", roomList);
```

### reservation-create.html
```java
model.addAttribute("room", room);
model.addAttribute("reservationCreateRequest", new ReservationCreateRequest());
```

### my-reservations.html
```java
model.addAttribute("reservations", reservationList);
```

### payment-create.html
```java
model.addAttribute("reservation", reservation);
model.addAttribute("paymentCreateRequest", new PaymentCreateRequest());
```

### my-payments.html
```java
model.addAttribute("payments", paymentList);
```

### room-add.html / room-edit.html
```java
model.addAttribute("roomCreateRequest", new RoomCreateRequest());
// or
model.addAttribute("roomEditRequest", new RoomEditRequest());
model.addAttribute("roomId", roomId); // for edit
```

### admin-rooms.html
```java
model.addAttribute("rooms", roomList);
```

### profile.html
```java
model.addAttribute("user", user);
```

### edit-profile.html
```java
model.addAttribute("userEditRequest", new UserEditRequest());
```

## 🔐 Session Attributes

Always set in session after login:
```java
session.setAttribute("userId", user.getId());
session.setAttribute("userName", user.getFirstName() + " " + user.getLastName());
session.setAttribute("userRole", user.getRole().name()); // "USER" or "ADMIN"
session.setAttribute("userEmail", user.getEmail());
session.setAttribute("userPhone", user.getPhoneNumber());
```

Used in templates for conditional rendering:
```thymeleaf
th:if="${session.userId != null}"     <!-- Is logged in -->
th:if="${session.userRole == 'ADMIN'}" <!-- Is admin -->
```

## 🎨 CSS Color Scheme

```css
--primary-color: #1a1a1a      /* Black */
--secondary-color: #d4af37    /* Gold - luxury accent */
--tertiary-color: #f5f5f5     /* Light gray */
--text-dark: #333
--text-light: #666
--text-white: #fff
--success-color: #27ae60      /* Green */
--danger-color: #e74c3c       /* Red */
--warning-color: #f39c12      /* Orange */
--info-color: #3498db         /* Blue */
```

## 🔘 Button Classes

```html
<!-- Variants -->
<button class="btn btn-primary">Primary (Gold)</button>
<button class="btn btn-secondary">Secondary (Black + Gold border)</button>
<button class="btn btn-success">Success (Green)</button>
<button class="btn btn-danger">Danger (Red)</button>
<button class="btn btn-warning">Warning (Orange)</button>
<button class="btn btn-info">Info (Blue)</button>

<!-- Sizes -->
<button class="btn btn-small">Small</button>

<!-- Special -->
<button class="btn-logout">Logout (Red button)</button>
```

## 📝 Form Binding Example

```html
<!-- Model object: userRegisterRequest -->
<form th:object="${userRegisterRequest}" th:action="@{/register}" method="post">
    <div class="form-group">
        <label for="username">Username</label>
        <input type="text" id="username" th:field="*{username}" required>
        <span th:if="${#fields.hasErrors('username')}" 
              class="form-error" th:errors="*{username}"></span>
    </div>
</form>
```

## 🏷️ Status Badges

```html
<!-- Reservation status -->
<span class="reservation-status status-active">ACTIVE</span>
<span class="reservation-status status-pending">PENDING</span>
<span class="reservation-status status-cancelled">CANCELLED</span>
<span class="reservation-status status-completed">COMPLETED</span>

<!-- Room status -->
<span class="reservation-status status-active">AVAILABLE</span>
<span class="reservation-status status-pending">OCCUPIED</span>
```

## 🎯 Thymeleaf Index

| Feature | Syntax |
|---------|--------|
| Variable | `${varName}` |
| Property | `${obj.property}` |
| URL link | `th:href="@{/path}"` |
| Image | `th:src="@{/path/to/image.jpg}"` |
| Form action | `th:action="@{/form-submit}"` |
| Form object | `th:object="${dto}"` |
| Form field | `th:field="*{fieldName}"` |
| Errors | `th:errors="*{fieldName}"` |
| Iteration | `th:each="item : ${list}"` |
| Conditional | `th:if="${condition}"` |
| Check empty | `#lists.isEmpty(list)` |
| Check errors | `#fields.hasErrors('field')` |
| Sum aggregation | `#aggregates.sum(list.![property])` |

## 🔄 Dynamic Payment Methods

```html
<!-- In payment-create.html -->
<select id="paymentType" th:field="*{paymentType}" onchange="updatePaymentMethods()">
    <option value="ONLINE">Online Payment</option>
    <option value="AT_HOTEL">Pay at Hotel</option>
</select>

<!-- JavaScript logic:
    ONLINE     → Show: BANK_TRANSFER
    AT_HOTEL   → Show: CARD, CASH
-->
```

## 📐 Grid Layouts

```html
<div class="grid grid-2">          <!-- 2 columns -->
    <div>Column 1</div>
    <div>Column 2</div>
</div>

<div class="grid grid-3">          <!-- 3 columns (rooms page) -->
    <div>Item 1</div>
    <div>Item 2</div>
    <div>Item 3</div>
</div>

<div class="grid grid-4">          <!-- 4 columns -->
    <div>Item 1</div>
</div>

<!-- Responsive: Stacks to 1 column on tablets/mobile -->
```

## 🔍 Responsive Breakpoints

```css
Desktop:   1200px+
Tablet:    768px - 1199px
Mobile:    480px - 767px
Small:     < 480px
```

## ✅ Validation Display

```html
<!-- Form group with error -->
<div class="form-group">
    <input th:field="*{email}">
    <!-- Shows red error text if validation fails -->
    <span th:if="${#fields.hasErrors('email')}" 
          class="form-error" 
          th:errors="*{email}"></span>
</div>

<!-- Alert messages at top of page -->
<div class="alert alert-success" th:if="${successMessage}">
    <p th:text="${successMessage}"></p>
</div>

<div class="alert alert-danger" th:if="${errorMessage}">
    <p th:text="${errorMessage}"></p>
</div>
```

## 🧮 Price Calculator (JavaScript)

In `reservation-create.html`:
```javascript
// Runs when check-in or check-out dates change
const nights = (checkOut - checkIn) / (1000 * 60 * 60 * 24);
const total = nights * pricePerNight;
// Updates on page in real-time
```

## 🛡️ Confirmation Modals

```html
<!-- Delete with confirmation -->
<form method="post" th:action="@{/admin/rooms/{id}/delete(id=${room.id})}"
      onsubmit="return confirm('Are you sure you want to delete this room?');">
    <button type="submit" class="btn btn-danger">Delete</button>
</form>
```

## 📱 Mobile Considerations

- Buttons: Touch-friendly (44px+ height)
- Forms: Single column on mobile
- Tables: Horizontal scroll or card view
- Navigation: Hamburger menu (can be added)
- Images: Responsive sizes

## 🎯 Quick Checklist for Backend Dev

- [ ] Create UserLoginRequest DTO
- [ ] Create UserRegisterRequest DTO
- [ ] Create UserEditRequest DTO
- [ ] Create RoomCreateRequest DTO
- [ ] Create RoomEditRequest DTO
- [ ] Create ReservationCreateRequest DTO
- [ ] Create PaymentCreateRequest DTO
- [ ] Create login controller (GET/POST /login)
- [ ] Create register controller (GET/POST /register)
- [ ] Create home controller (GET /home)
- [ ] Create rooms controller (GET /rooms, /rooms/{id})
- [ ] Create admin rooms controller (GET/POST operations)
- [ ] Create reservations controller
- [ ] Create payments controller
- [ ] Create profile controller
- [ ] Set up session management
- [ ] Set up authentication/authorization
- [ ] Create database images directory
- [ ] Add placeholder images
- [ ] Configure Spring Security

---

**For detailed information, see: FRONTEND_DOCUMENTATION.md**

