import java.util.*;

abstract class Akun {
    private String username;
    private String password;

    public Akun(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public boolean autentikasi(String password) {
        return this.password.equals(password);
    }
}

class Admin extends Akun {
    public Admin(String username, String password) {
        super(username, password);
    }

    public void lihatOrders(List<Order> orders) {
        if (orders.isEmpty()) {
            System.out.println("Belum ada pendaftaran untuk event ini.");
        } else {
            System.out.println("Daftar pendaftar:");
            for (Order order : orders) {
                System.out.println("User: " + order.getUser().getUsername() + " - Event: " + order.getEvent().getNama());
            }
        }
    }
}

class User extends Akun {
    private List<Order> riwayatOrder = new ArrayList<>();

    public User(String username, String password) {
        super(username, password);
    }

    public List<Order> getRiwayatOrder() {
        return riwayatOrder;
    }

    public void lihatRiwayatOrder() {
        if (riwayatOrder.isEmpty()) {
            System.out.println("Belum ada pendaftaran untuk event.");
        } else {
            System.out.println("Riwayat Pendaftaran Anda:");
            for (Order order : riwayatOrder) {
                System.out.println(order);
            }
        }
    }

    public void batalkanPendaftaran(String namaEvent) {
        for (Order order : riwayatOrder) {
            if (order.getEvent().getNama().equals(namaEvent)) {
                riwayatOrder.remove(order);
                System.out.println("Pendaftaran untuk event '" + namaEvent + "' berhasil dibatalkan.");
                return;
            }
        }
        System.out.println("Pendaftaran untuk event '" + namaEvent + "' tidak ditemukan.");
    }
    
    public void lihatDetailEvent(String namaEvent) {
        for (Event event : ManajemenEvent.daftarEvent) {
            if (event.getNama().equalsIgnoreCase(namaEvent)) {
                System.out.println("Detail Event: ");
                System.out.println(event);
                return;
            }
        }
        System.out.println("Event dengan nama '" + namaEvent + "' tidak ditemukan.");
    }
}

class Event {
    private String nama;
    private String kategori;
    private int stokTiket;
    private String status;
    private String tanggal;

    public Event(String nama, String kategori, int stokTiket, String status, String tanggal) {
        this.nama = nama;
        this.kategori = kategori;
        this.stokTiket = stokTiket;
        this.status = status;
        this.tanggal = tanggal;
    }

    public String getNama() {
        return nama;
    }

    public String getKategori() {
        return kategori;
    }

    public int getStokTiket() {
        return stokTiket;
    }

    public void setStokTiket(int stokTiket) {
        this.stokTiket = stokTiket;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTanggal() {
        return tanggal;
    }

    public void setTanggal(String tanggal){
        this.tanggal = tanggal;
    }

    @Override
    public String toString() {
        return "Nama Event: " + nama + ", Kategori: " + kategori + ", Stok Tiket: " + stokTiket + ", Status: " + status + ", Tanggal: " + tanggal;
    }
}

class Order {
    private User user;
    private Event event;

    public Order(User user, Event event) {
        this.user = user;
        this.event = event;
    }

    public User getUser() {
        return user;
    }

    public Event getEvent() {
        return event;
    }

    @Override
    public String toString() {
        return "Event: " + event.getNama() + ", Tanggal: " + event.getTanggal();
    }
}

public class ManajemenEvent {
    public static List<Event> daftarEvent = new ArrayList<>();
    private static List<User> daftarUser = new ArrayList<>();
    private static List<Order> orders = new ArrayList<>();
    private static Admin admin = new Admin("ad", "ad");

    public static void tambahEvent(String nama, String kategori, int stokTiket, String status, String tanggal) {
        Event event = new Event(nama, kategori, stokTiket, status, tanggal);
        daftarEvent.add(event);
    }

    public static void lihatEvent() {
        for (Event event : daftarEvent) {
            System.out.println(event);
        }
    }

    public static void editEvent(String nama, int stokBaru, String statusBaru, String tanggalBaru) {
        for (Event event : daftarEvent) {
            if (event.getNama().equalsIgnoreCase(nama)) {
                event.setStokTiket(stokBaru);
                event.setStatus(statusBaru);
                event.setTanggal(tanggalBaru);
                System.out.println("Event berhasil diperbarui!");
                return;
            }
        }
        System.out.println("Event tidak ditemukan!");
    }

    public static void daftarEvent(User user, String namaEvent) {
        for (Event event : daftarEvent) {
            if (event.getNama().equalsIgnoreCase(namaEvent)) {
                if (event.getStokTiket() > 0 && event.getStatus().equalsIgnoreCase("Aktif")) {
                    event.setStokTiket(event.getStokTiket() - 1);
                    orders.add(new Order(user, event));
                    user.getRiwayatOrder().add(new Order(user, event));
                    System.out.println("Pendaftaran berhasil!");
                    return;
                } else {
                    System.out.println("Tiket habis atau event tidak aktif!");
                }
            }
        }
        System.out.println("Event tidak ditemukan!");
    }

    public static void searchEvent(String keyword, String jenisPencarian) {
        boolean found = false;
        for (Event event : daftarEvent) {
            if (jenisPencarian.equalsIgnoreCase("nama") && event.getNama().toLowerCase().contains(keyword.toLowerCase())) {
                System.out.println(event);
                found = true;
            } else if (jenisPencarian.equalsIgnoreCase("kategori") && event.getKategori().toLowerCase().contains(keyword.toLowerCase())) {
                System.out.println(event);
                found = true;
            } else if (jenisPencarian.equalsIgnoreCase("semua") &&
                       (event.getNama().toLowerCase().contains(keyword.toLowerCase()) || 
                        event.getKategori().toLowerCase().contains(keyword.toLowerCase()))) {
                System.out.println(event);
                found = true;
            }
        }
        
        if (!found) {
            System.out.println("Event tidak ditemukan dengan keyword: " + keyword);
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        daftarUser.add(new User("u1", "12"));
        daftarUser.add(new User("u2", "12"));
        daftarUser.add(new User("u3", "12"));

        while (true) {
            System.out.println("\nMenu:");
            System.out.println("1. Login sebagai Admin");
            System.out.println("2. Login sebagai User");
            System.out.println("3. Keluar");
            System.out.print("Pilih menu: ");
            int pilihan = scanner.nextInt();
            scanner.nextLine();

            if (pilihan == 1) {
                System.out.print("Masukkan username Admin: ");
                String username = scanner.nextLine();
                System.out.print("Masukkan password Admin: ");
                String password = scanner.nextLine();

                if (admin.getUsername().equals(username) && admin.autentikasi(password)) {
                    System.out.println("Login berhasil sebagai Admin!");
                    while (true) {
                        System.out.println("1. Tambah Event");
                        System.out.println("2. Lihat Event");
                        System.out.println("3. Edit Event");
                        System.out.println("4. Lihat Pendaftar Event");
                        System.out.println("5. Logout");
                        System.out.print("Pilih aksi: ");
                        int aksi = scanner.nextInt();
                        scanner.nextLine();

                        if (aksi == 1) {
                            System.out.print("Nama event: ");
                            String nama = scanner.nextLine();
                            System.out.print("Kategori: ");
                            String kategori = scanner.nextLine();
                            System.out.print("Stok tiket: ");
                            int stok = scanner.nextInt();
                            scanner.nextLine();
                            System.out.print("Status: ");
                            String status = scanner.nextLine();
                            System.out.print("Tanggal: ");
                            String tanggal = scanner.nextLine();

                            tambahEvent(nama, kategori, stok, status, tanggal);
                            System.out.println("Event berhasil ditambahkan!");
                        } else if (aksi == 2) {
                            lihatEvent();
                        } else if (aksi == 3) {
                            System.out.print("Nama event yang akan diedit: ");
                            String nama = scanner.nextLine();
                            System.out.print("Stok baru: ");
                            int stokBaru = scanner.nextInt();
                            scanner.nextLine();
                            System.out.print("Status baru: ");
                            String statusBaru = scanner.nextLine();
                            System.out.print("Tanggal baru: ");
                            String tanggalBaru = scanner.nextLine();

                            editEvent(nama, stokBaru, statusBaru, tanggalBaru);
                        } else if (aksi == 4) {
                            System.out.print("Masukkan nama event untuk melihat pendaftar: ");
                            String namaEvent = scanner.nextLine();
                            admin.lihatOrders(orders);
                        } else if (aksi == 5) {
                            System.out.println("Keluar dari Admin.");
                            break;
                        }
                    }
                } else {
                    System.out.println("Username atau password Admin salah!");
                }
            } else if (pilihan == 2) {
                System.out.print("Masukkan username: ");
                String username = scanner.nextLine();
                System.out.print("Masukkan password: ");
                String password = scanner.nextLine();

                User user = null;
                for (User u : daftarUser) {
                    if (u.getUsername().equals(username) && u.autentikasi(password)) {
                        user = u;
                        break;
                    }
                }

                if (user != null) {
                    System.out.println("Login berhasil sebagai User!");
                    while (true) {
                        System.out.println("1. Cari Event");
                        System.out.println("2. Lihat Detail Event");
                        System.out.println("3. Pendaftaran Event");
                        System.out.println("4. Lihat Riwayat Pendaftaran");
                        System.out.println("5. Batalkan Pendaftaran");
                        System.out.println("6. Logout");
                        System.out.print("Pilih aksi: ");
                        int aksi = scanner.nextInt();
                        scanner.nextLine();

                        if (aksi == 1) {
                            System.out.println("Pilih jenis pencarian:");
                            System.out.println("1. Berdasarkan Nama");
                            System.out.println("2. Berdasarkan Kategori");
                            System.out.println("3. Berdasarkan Nama dan Kategori");
                            System.out.print("Pilih jenis pencarian: ");
                            int jenisPencarianChoice = scanner.nextInt();
                            scanner.nextLine();
                        
                            String jenisPencarian = "";
                            if (jenisPencarianChoice == 1) {
                                jenisPencarian = "nama";
                            } else if (jenisPencarianChoice == 2) {
                                jenisPencarian = "kategori";
                            } else if (jenisPencarianChoice == 3) {
                                jenisPencarian = "semua";
                            } else {
                                System.out.println("Pilihan tidak valid.");
                                continue;
                            }
                        
                            System.out.print("Masukkan keyword untuk cari event: ");
                            String keyword = scanner.nextLine();
                            searchEvent(keyword, jenisPencarian);
                        } else if (aksi == 2) {
                            System.out.print("Masukkan nama event untuk melihat detail: ");
                            String namaEvent = scanner.nextLine();
                            user.lihatDetailEvent(namaEvent);
                        } else if (aksi == 3) {
                            System.out.print("Masukkan nama event untuk pendaftaran: ");
                            String namaEvent = scanner.nextLine();
                            daftarEvent(user, namaEvent);
                        } else if (aksi == 4) {
                            user.lihatRiwayatOrder();
                        } else if (aksi == 5) {
                            System.out.print("Masukkan nama event untuk dibatalkan: ");
                            String namaEvent = scanner.nextLine();
                            user.batalkanPendaftaran(namaEvent);
                        } else if (aksi == 6) {
                            System.out.println("Keluar dari User.");
                            break;
                        }
                    }
                } else {
                    System.out.println("Username atau password salah!");
                }
            } else if (pilihan == 3) {
                System.out.println("Keluar dari program.");
                break;
            } else {
                System.out.println("Pilihan tidak valid.");
            }
        }

        scanner.close();
    }
}
