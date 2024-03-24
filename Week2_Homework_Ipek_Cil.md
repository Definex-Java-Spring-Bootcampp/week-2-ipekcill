
# Senkron ve Asenkron İletişim

## Senkron İletişim
Senkron iletişim eş zamanlı iletişim anlamına gelir. Senkron iletişimde bir microservice işlemlerini yaparken farklı bir servisten bir bilgi ihtiyacı olduğunda o servise istek (request) atar ve cevabını (response) bekler. Gelen cevap sonucunda ise işlemlerine devam eder.
İsteğin cevabı gelene kadar diğer işlemler beklemek zorundadr.Bir servis diğerine istekte bulunduğunda ve servis yanıtı geciktiğinde ya da hiç cevap veremediğinde istekte bulunan servis cevap beklediği için işlem başarısız olabilir.Yoğun isteklerin bulunduğu yapılarda bu durum sistemde yığılmaya neden olur.
Bu tarz durumlara karşı farklı çözümler bulunmaktadır ancak uygulanması için zaman ve efor anlamında maaliyet gerektirir. Microservice mimarisinde farklı servisler arası iletişim söz konusudur. Kodları yazarken loosely coupled mantığıyla hareket etmek gerekir. Microservisler arası iletişimde de bu durum çok öenmlidir.
Senkron iletişimdeki bloklanma, hata oluşma ve bağımlılık gibi nedenlerden dolayı mimari içindeki senkron iletişimlerden sistemi olabildiğinde arındırmak gereklidir. Bunun için cevabın kesinlikle zorunlu olduğu durumlarda kullanılabilir. 
Bir ödeme işlemi ya da authentication işleminin farklı serviste konumlandıysa senkron yapıda olması gerekir.İşlemlerin doğruluğu kesinleşmek zorundadır.
Microservisler arası senkron iletişim HTTP ile sağlanır. HTTP, Rest ve SOAP ile uygulanabilir. En yaygın olanı Restful ile HTTP üzerinden haberleşmedir. Bir rest çağrısı yapılırken cevap beklenir ve asıl olan işlemi gerçekleştirmek için cevap gelmesini beklemek işlemi bloklamaktadır. 

Bir ödeme gerçekleştiğinde bu bilgi Payment servisine iletilir. 
Ödeme işlemi için bankayla iletişime geçmek üzere bilgiler Banking servisine gönderilir.
Banking servisine gelen bilgiler ile ödeme gerçekleştiğinde banking servisi bir response üretir.
Üretilen response’u hali hazırda cevabı bekleyen payment servisi alarak gerekli işlemleri kendi içinde gerçekleştirir ve kullanıcıya cevabını döner.
Payment servisinden request gönderilirken ya da banking servisinde bir hata oluşma durumunda fallback çalıştırılır. Ancak payment servisi cevap alana kadar işlemde olan thread bloke olur.

![Senkron Payment Request](https://miro.medium.com/v2/resize:fit:720/format:webp/1*BB3G8KmqWn5W3m3A7KtKOg.png)

## Asenkron İletişim
Asenkron iletişim, işlemlerin birbirinden bağımsız olarak gerçekleştirildiği ve bir işlem tamamlanırken diğerinin beklemesi gerekmediği bir iletişim şeklidir. Bu iletişim modelinde, gönderen ve alıcı arasında bir senkronizasyon sağlanmaz ve veri transferi arka planda, istemci tarafından sürekli olarak beklenmeden gerçekleşir.
Örneğin, bir e-ticaret uygulamasında siparişin işlenmesi işlemi asenkron iletişim ile gerçekleştirilebilir. 
Bir kullanıcı sipariş verdiğinde, sipariş bilgileri doğrudan bir kuyruk sistemi aracılığıyla bir mesaj kuyruğuna (örneğin, RabbitMQ veya Kafka gibi) gönderilir. Bu sipariş bilgileri, siparişin tamamlanması için gerekli işlemleri gerçekleştirecek olan bir sipariş işleme servisine iletilir. Sipariş işleme servisi, sipariş bilgilerini alır, siparişin hazırlanması, paketlenmesi ve gönderilmesi gibi işlemleri gerçekleştirir. 
Bu süreç, sipariş veren kullanıcıya anında bir yanıt döndürmek yerine, sipariş işleme servisinin siparişi tamamlamasını bekleyerek gerçekleşmez. Bu sayede, sipariş işleme süreci asenkron olarak gerçekleştirilir ve sipariş veren kullanıcıya hızlı bir şekilde yanıt verilirken, arkadaki işlemler bağımsız olarak çalışır ve verimli bir şekilde işlenir. 
Bu da ölçeklenebilirlik ve performans açısından önemli avantajlar sağlar.

Asenkron iletişim ayrıca bire çok (one-to-many) iletişim yapılabilmesine de olanak sağlar, yani bir mesaj atıldığında birden fazla servise ulaşması sağlanabilir. 
Senkron iletişimde ise client her bir farklı servise tek tek istekte bulunmak durumundadır.

**"Notification"** ve **"Request/Non-blocking Response"** kavramları, asenkron iletişimde kullanılan farklı iletişim modellerini ifade eder.

-**Notification**
Bir bildirim, genellikle bir olayın veya durumun diğer bileşenlere veya sistemlere iletilmesi amacıyla kullanılan bir iletişim şeklidir. 
Bildirimler, genellikle alıcıdan bir yanıt beklenmeksizin gönderilir ve alıcılar bu bildirimi aldıklarında belirli bir eylem gerçekleştirirler.
Örneğin, bir dosya indirme işlemi tamamlandığında, indirme işlemi bittiğinde bir bildirim gönderilebilir. 
Bu bildirim, kullanıcıya dosyanın indirildiğini bildirir ve kullanıcı isteğe bağlı olarak dosyayı açabilir veya başka bir işlem yapabilir.
Direkt olarak diğer servise yapılan bir asenkron istek başarılı sayılarak bloklanmadan devam edilir ve dolayısıyla bağımlılık aza indirgenmiş olur yani loosely coupled yapıda olduğunu gösterir.

-**Request/Non-blocking Response**
Bu iletişim modelinde, bir istemci bir istek gönderir ve bu istek hemen işlenir. İşlem sırasında istemci, yanıtı beklemek yerine diğer işlemlere devam edebilir. İstek yapıldıktan sonra, sunucu işlemi tamamlar ve bir yanıt oluşturur. Bu yanıt daha sonra istemciye iletilir. Bu süreçte, istemciye engellenmeyen bir yanıt gönderilir, yani istemci, yanıtı beklemek yerine diğer işlemleri gerçekleştirmeye devam eder.
Örneğin, bir web sayfası, bir HTTP isteği gönderdiğinde ve isteğe bağlı olarak bir dosya indirme işlemi gerçekleştirildiğinde, bu indirme işlemi non-blocking (engellenmeyen) olarak gerçekleştirilebilir. 
İstek gönderildikten sonra, indirme işlemi sunucuda gerçekleştirilir ve dosya indirildikten sonra istemciye bir yanıt gönderilir. 
Bu süreçte, istemci, diğer işlemleri gerçekleştirmeye devam eder ve dosya indirme işlemi tamamlandığında bir bildirim alır veya kullanıcıya bir uyarı gösterilir. 
Bu, kullanıcının web sayfasında diğer etkileşimlerine devam edebilmesine olanak tanır.

![Notification example](https://miro.medium.com/v2/resize:fit:720/format:webp/1*Qv-0gDNjzlMfOWve-rTfbA.png)

# RabbitMQ vs Kafka

1. RabbitMQ
RabbitMQ mesaj kuyruğu (message queue) sistemidir. Yazdığımız programımız üzerinden yapılacak asenkron (asynchronous) işlemleri sıraya koyup, bunları sırayla kuyruktan çekip gerçekleyerek ilerleyen ölçeklenebilir ve performanslı bir sistemdir.
RabbitMQ bir çok yazılım diline destek vermektedir, bir çok işletim sistemi üzerinde çalışabilmektedir ve open source’dur.

RabbitMQ'nun kullanım alanları şunlardır:
-Mikro hizmet mimarilerinde, farklı hizmetler arasında asenkron ve güvenilir iletişim sağlamak için.
-İş akışı yönetimi ve görev kuyrukları için, örneğin, işlerin sırayla ve paralel olarak işlenmesi için.
-Yük dengeleme ve hata toleransı sağlamak için.
-RabbitMQ, güvenilirlik, esneklik ve ölçeklenebilirlik gibi özellikleriyle modern dağıtık sistemlerin temel bileşenlerinden biridir.

2. Kafka
Apache Kafka, verilerin bir sistemden hızlı bir şekilde toplanıp diğer sistemlere hatasız bir şekilde transferini sağlamak için geliştirilen dağıtık bir veri akış mekanizmasıdır. 
Başlangıçta 2011’de Linkedin tarafından Java ile geliştirilen Kafka daha sonra Apache çatısı altında açık kaynak bir projeye dönüştürülmüştür. 
Günümüzde Linkedin, Netflix, Uber, Twitter gibi devasa boyutlarda veriye sahip olan birçok firma tarafından kullanılmaktadır.
Kafka, büyük ölçekli veri akışlarını işlemek, depolamak ve yeniden kullanmak için tasarlanmıştır. Özellikle gerçek zamanlı veri akışlarının işlenmesi ve analiz edilmesi için ideal bir platformdur.

Genel çerçeveden baktığımızda messaging queue'ler bir sender-receiver şeklinde çalışırlar.

## RabbitMQ ve Kafka arasındaki farklılıklar
İki teknoloji de farklı ihtiyaçlara cevap vermektedir.
1. Veri akışı
RabbitMQ farklı, sınırlı bir veri akışı kullanır. Mesajlar üretici tarafından oluşturulup gönderilir ve consumer tarafından alınır. Apache Kafka, key/value çiftlerinin sürekli olarak atanan konuya akışıyla sınırsız bir veri akışı kullanır.
2. Veri kullanımı
RabbitMQ, sipariş oluşturma ve yerleştirme ve kullanıcı istekleri gibi işlem verileri için en iyisidir. Kafka; süreç işlemleri, denetim ve günlük istatistikleri ve sistem etkinliği gibi operasyonel verilerle en iyi şekilde çalışır.
3. Mesajlaşma
RabbitMQ kullanıcılara mesaj gönderir. Bu mesajlar işlenip onaylandıktan sonra kuyruktan kaldırılır. Saklama süresi dolana kadar kuyrukta kalan sürekli mesajları kullanır.
4. Tasarım Modeli
RabbitMQ akıllı smart broker/dumb consumer modelini kullanır. Broker sürekli olarak tüketicilere mesajlar iletir ve durumlarını takip eder. Kafka aptal dumb broker/smart consumer modelini kullanıyor. Kafka, her kullanıcının okuduğu mesajları izlemez. Bunun yerine, yalnızca okunmamış mesajları saklar ve tüm mesajları belirli bir süre boyunca saklar.
5. RabbitMQ, exchange queue topolojisini kullanır; mesajları bir exchange'e gönderir ve burada mesajlar, consumer'ın kullanımı için çeşitli queue bindings'e yönlendirilir. Kafka, publish/subscribe topolojisini kullanarak akış üzerinden doğru konulara mesaj gönderiyor ve ardından farklı yetkili gruplardaki kullanıcılar tarafından tüketiliyor.
6. Ölçeklenebilirlik ve Redundancy
RabbitMQ, mesajları tekrarlamak için bir kez deneme kuyruğu kullanır. Verimi artırmak ve yükü dengelemek için mesajlar kuyruklar arasında bölünür. Ek olarak, çok sayıda tüketicinin çeşitli kuyruklardaki mesajları aynı anda okumasına olanak tanır.
Ölçeklenebilirlik ve yedeklilik Kafka bölümleri tarafından sağlanır. Brokerlardan birinin başarısız olması durumunda müşteriye başka bir brokerdan hizmet verilmeye devam edilebilir.
Tüm bölümleri tek bir aracıda saklarsak, o aracıya olan bağımlılığımız artacaktır, bu da tehlikelidir ve başarısız olma olasılığını artırır. Ek olarak, bölümleri dağıtmak verimi büyük ölçüde artıracaktır.
7. Mesaj Önceliği
RabbitMQ'daki öncelik sırası yardımıyla mesajlara öncelik verilebilir.
Kafka'da tüm mesajlar değiştirilemeyen aynı önceliğe sahiptir.

# Docker - Virtual Machine

Sanal makine (Virtual Machine - VM) ve Docker, her ikisi de uygulamaların geliştirilmesi, dağıtılması ve çalıştırılması için kullanılan farklı sanallaştırma teknolojileridir. Her iki teknoloji de uygulamaları izole edilmiş ve taşınabilir ortamlara yerleştirmeyi sağlar, ancak farklı yaklaşımlar kullanırlar.
Son yıllarda Docker birçok yazılım projesinde sıklıkla kullanılmaktadır. Yazılım dünyasındaki sorunları ortadan kaldırmak için çözümler üretilmiştir. Bu sorunlardan biri ortam değişkenidir (Örnek: Yazılım sürümleri). Yazdığımız kod bizim bilgisayarımızda sorunsuz çalışmakta, test etmesi için tester'a verdiğimze tester'ın sisteminde çalışmayabilir. Bunun nedeni yazdığımız kodun çalışması için gerekli ortam ve konfigürasyonun birebir aynısı karşı tarafta olmadığındandır.
Başka bir örnek verecek olursam; farklı uygulamalar farklı gereksinimlere ihtiyaç duyar. Farklı şekillerde bu sorun çözülebilir ama zamanla bu sistem zombi sistem haline gelir ve kimse değiştirmek istemez. Bu sebeple programların birbirinden izole bir şekilde çalışması için pek çok uygulama geliştirilmiştir.
Bu çözümlerden biri **Virtual Machine** (Sanal Makine)'dir.

## Virtual Machine
Sanal Makine (Virtual Machine) veya kısaca VM; dizüstü bilgisayar, akıllı telefon, sunucu vb. fiziksel bilgisayarlardan tamamen farklı değildir. Sanal Makine de tıpkı klasik bir PC gibi dosyalarınızı depolayabilmek için CPU, bellek ve disklere sahiptir.
Sanal Makinenin nasıl çalıştığını anlamak için öncelikle **sanallaştırma** teriminden bahsetmek gereklidir. **Sanallaştırma**, kişisel bilgisayarınız gibi fiziki bir bilgisayardan veya bir bulut sağlayıcısının veri merkezinde bulunan bir sunucu gibi uzak bir sunucudan ödünç alınan, ayrılmış miktarlarda CPU, depolama ve bellek ile bir PC’nin yazılım tabanlı veya “sanal” bir versiyonu oluşturma işlemidir.
Sanal makine ise tıpkı gerçek bir bilgisayar gibi hareket edebilen bir bilgisayar dosyasıdır. Söz konusu dosyaya ise “görüntü” denir. Görüntü adlı bu dosya pek çok kullanıcının iş bilgisayarında yaygın şekilde olduğu gibi, bir pencerede, çoğu zaman farklı bir işletim sisteminin çalıştırılması amacıyla veya kullanıcının tüm bilgisayar deneyimi olarak işlev görmesini sağlamak için ayrı bir bilgi işlem ortamı şeklinde işlev gösterebilir.
Sistemin geri kalan kısmından bölümlenmiş olan VM, ana bilgisayarın birincil işletim sisteminden izoledir ve bu sisteme müdahil olmaz.
Bir bilgisayar üzerinde sanal birkaç cihaz oluştururuz ve bu cihazların içerisine Java'nın ya da kullandığımız toolun, programlama dilinin farklı sürümlerini kullanabiliriz. Ancak sanal makine maaliyetlidir. Daha fazla kaynak ister. Bu kaynaklardan biri hafızadır. Cihazı yorar.Bazı farklı işlemler yapmak zorunda kalırız ve bu bizi uğraştırır.
**Ne kazandırır?**
Geliştirme uzmanlarının geliştirme ve test senaryolarını çalıştırabilmesini pratik, kolay ve hızlı kılmak amacıyla bir yeni ortam oluşturulmasını sağlar.
Uygulama ve/veya yazılımlarım, başlangıçta hedeflendikleri işletim sistemlerinde çalıştırılmasını sağlar.
Çok fazla sayıda küçük iş yükünün bir tek fiziki bilgisayar üzerinde birleştirilmesi, verimliliğin maksimize edilmesini sağlar. Bu birleştirme işlemi ise sanallaştırma sayesinde mümkündür.

## Docker
2013 yılında sanal makineden çok daha iyi olduğu düşünülerek Docker öne sürüldü. Golang programlama dili kullanarak açık kaynaklı olarak geliştirilen Docker'da bizim de kodlarımız olabilir. Koyteynerlaşma kullanarak uygulama oluşturma ve dağıtmayı sağlayan bir araçtır.
Yazılımı ve yazılımın doğru bir şekilde çalışmasını sağlayan değişkenlerin bir arada olduğu bir paket birimidir. Bunun amacı başta söylediğim problemi ortadan kaldırmak, uygulamanın ortam değişkeninden bağımsız, çok daha hızlı ve güvenilir bir şekilde bir yerden bir yere aktarmaktadır. Docker ile ortamları sanallaştırırken fazladan işletim sistemi katmanının ortaya çıkmasını engeller.
Bu sayede başta hafıza olmak üzere birçok şeyden performans ve maaliyet olarak da çok daha karlı olabiliriz. Sanallaştırmada Hyper Visor komponenti bulunmaktadır. Docker'da bu komponentin yerini **Container Engine** almaktadır. Docker neden bu kadar popülerleşti diye düşünecek olursak, bunun sebebi **kullanımı sanal makineye göre daha kolay olmasıdır**. 
Çok daha fazla **performanslı kullanım** sunar.
Daha **hızlı yazılım teslimi** sağlar. 
Daha **hızlı ölçeklendirme** sağlar
**Microservice mimarisiyle mükemmel bir uyum** şeklinde çalışmaktadır.

Özetle, sanal makineler tam işletim sistemleri üzerinde çalışırken, Docker konteynerleri daha hafif ve hızlı bir şekilde işletim sistemi seviyesinde çalışır. Her iki teknoloji de farklı senaryolarda kullanılabilmektedir, ancak Docker genellikle mikro servis mimarileri ve bulut tabanlı uygulama dağıtımları gibi modern uygulama geliştirme yaklaşımlarında daha yaygın olarak kullanılmaktadır.

# Microservice ve Monotlith Mimarileri      

## Monolith Mimari

Monolitik bir uygulama, birden fazla modül içeren tek bir kod tabanına sahiptir. 
Modüller, fonksiyonel veya teknik özelliklerine göre ayrılmıştır. 
Tüm uygulamayı build eden tek bir derleme sistemine sahiptir. 
Ayrıca tek bir çalıştırılabilir veya deploy edilebilir dosyaya sahiptir.
Genellikle, uygulamalar üç kısımdan oluşmakta; client kısmı yani kullanıcıya gösterilen ve kullanıcının işlemlerinin gerçekleştirildiği kısım web sayfası, mobil uygulama gibi, bir de sunucu uygulaması kısmı tüm isteklerin yönetildiği ve gerekli algoritmaların bulunduğu yapı olarak düşünülebilir ve son olarak verileri kaydettiğimiz ve depoladığımız database kısmından oluştuğu söylenebilir. 
Tek bir parçadan oluşan server kısmını düşündüğümüzde, sistemin tümüyle hareket ettiğini görebiliriz.
Başlatılacağı zaman ya da durdurulacağı zaman ve hatta çöktüğü zaman uygulamanın tamamı bu durumdan etkilenir. Yani uygulama, yaşamı boyunca tek bir parça halinde hareket eder. Herhangi bir güncelleme, değişiklik ya da versiyon yükseltmesi işlemi için uygulamanın tümüne müdahale etmemiz gerekir
Monolitik mimaride devasa bir kod tabanını kullanımı vardır. Bunun yanı sıra yeni bir teknoloji benimsemek, ölçeklendirmek, deployment süreçleri, yeni değişiklikler uygulamak gibi birtakım zorlukları mevcuttur. Uygulama tek bir ortamda çalıştırabilir ve test edilebilir. Aynı zaman da değişikliklerden sonra tek bir dağıtım(deployment) metodu kullanarak çalışır hale getirilebilir.
Monolitik uygulamalar da başarılı olabilir, ancak artan internet kullanımı ile birlikte uygulamalara düşen trafik miktarı çok artmıştır. Bu durum monolitik mimarinin kullanımını zorlaştırır. Çünkü trafik altında ezilen tek bir proje trafiği kaldırabilmek için daha fazla kaynağa ihtiyaç duyar.
Uygulamanın küçük bir kısmında yapılan bir değişiklik, monolitik bir projenin tümünün yeniden derlenmesi ve tüm uygulamanın deploy edilmesini gerektirir. Bunun aksine, microservicelerde yalnızca değiştirdiğiniz hizmetleri yeniden build ve deploy etmemiz yeterlidir.
Ölçeklendirme zorluğu mevcuttur. Çünkü kaynağa ihtiyaç duyan modüller yerine tüm uygulamanın ölçeklendirmesi gerekir.

## Microservice
Microservice, tek işe odaklı uygulamanın işlevsel küçük bir parçasını ifade eder. Microservice olarak tasarlanan bir servis, ihtiyaca bağlı olarak birden fazla projede(uygulamada) veya farklı projelerde tekrar kullanılabilir. Servisler arasındaki bağımlılıklar, loose-coupled ilkesine uygun olarak en aza indirgenir. Tek sorumluluk odaklı servisler bir standarda uygun olunca, diğer servisler üzerindeki değişikliklerden etkilenmezler.
Aslında, microservice yaklaşımı ölçeklenebilirlik, esneklik, çeviklik gibi önemli avantajlar barındırır. Bu yüzden birçok önemli ve büyük şirket monolitik mimarilerinden microservice mimarisine geçiş yapmıştır. Bu arada, pek çok şirket bu yöntemi kullanmaya başlamakta ya da var olan sistemlerini bu mimariye evirmektedir.

![Monolith vs Microservice](https://media.licdn.com/dms/image/C5112AQFUP576tW55Eg/article-cover_image-shrink_720_1280/0/1559719981650?e=1716422400&v=beta&t=iG9lTUsJYV2w-NHYqh0XtEBqi3TED7_9dK1u1DObf0s)

# API Gateway, Service Discovery, Load Balancer  

## API Gateway
API Gateway, gelen API isteklerini alır, yönlendirir ve işler. API'ların dış dünyayla etkileşimini kolaylaştıran bir arayüz sağlar. 
API Gateway'nin bazı temel işlevleri şunlardır:
- Kimlik Doğrulama ve Yetkilendirme: API Gateway, gelen istekleri doğrular ve yetkilendirme işlemlerini gerçekleştirir. Bu sayede, güvenlik kontrolleri uygulanarak sadece yetkili kullanıcıların API'lara erişmesi sağlanır.
- İstek Yönlendirme: API Gateway, gelen istekleri doğru hedefe yönlendirir. Bu, belirli bir hedef sunucu, mikro hizmet veya işlevi çağırabilir ve gerekirse sonuçları birleştirerek istemciye döndürebilir.
- Protokol Dönüştürme: API Gateway, farklı protokoller arasında dönüşüm yapabilir. Örneğin, gelen istekleri HTTP'de alır ve arka planda çalışan mikro hizmetlerle iletişimi sağlamak için başka bir protokol olan TCP'ye dönüştürebilir.
- Önbellekleme ve Performans İyileştirmesi: API Gateway, sık kullanılan istekleri önbelleğe alarak performansı artırabilir. Sıkça istenen verileri önbelleğe alarak sunucu yükünü azaltır ve yanıt sürelerini düşürür.
- API Yönetimi: API Gateway, API'lara gelen istekleri izleyebilir, API analitiği sağlayabilir ve trafik kontrolü yapabilir. Ayrıca, API sürümlendirme, dokümantasyon oluşturma ve API güncellemelerini yönetme gibi işlevleri de sağlar.

## Load Balancer
Load balancer, bir uygulama veya hizmete gelen talepleri birden fazla sunucu veya hedef arasında eşit şekilde dağıtan bir bileşendir. Amacı, yükü daha verimli bir şekilde yönetmek ve hizmetin yüksek kullanılabilirliğini sağlamaktır.
- Yüksek Kullanılabilirlik: Load balancer, birden fazla sunucu arasında talepleri dağıtarak yükü dengelemesi yapar. Böylece, bir sunucunun başarısız olması durumunda diğer sunucular devreye girer ve hizmetin kesintiye uğramadan çalışmasını sağlar.
- Performans İyileştirmesi: Load balancer, sunucu kaynaklarını verimli bir şekilde kullanarak talepleri farklı sunuculara dağıtır. Bu sayede, yüksek talep durumunda daha iyi bir performans elde edilir.
- Uygulama Ölçeklendirme: Load balancer, yeni sunucular ekleyerek veya mevcut sunucuları kaldırarak uygulamanın ölçeklendirilmesini sağlar. Bu sayede, talepler arttığında daha fazla sunucu kullanılabilir ve uygulama daha fazla yükü kaldırabilir.

Load Balancer sunucular arasındaki yükü dengelemek için kullanılırken, API Gateway API'larınızı yönetmek, güvenlik sağlamak ve ek işlevler sunmak için kullanılır. İhtiyaçlara bağlı olarak, bu iki bileşeni ayrı ayrı veya birlikte kullanabiliriz.

## Service Discovery
Az çok karmaşık bir uygulamayı oluşturan birkaç microservice düşündüğümüzde bunlar birbirleriyle bir şekilde iletişim kuracaktır (örneğin, API Rest, gRPC).
Microservice tabanlı bir uygulama genellikle sanallaştırılmış veya kapsayıcıya alınmış ortamlarda çalışır. Bir hizmetin örneklerinin sayısı ve konumları dinamik olarak değişir. İsteklerin hedef mikro hizmete ulaşmasını sağlamak için bu örneklerin nerede olduğunu ve adlarını bilmemiz gerekir. Service Discovery gibi taktiklerin devreye girdiği yer burasıdır.
Service Discovery mekanizması her örneğin nerede bulunduğunu bilmemize yardımcı olur. Bu şekilde Service Discovery bileşeni, tüm örneklerin adreslerinin izlendiği bir kayıt defteri gibi düşünülebilir. Örneklerin dinamik olarak atanmış ağ yolları vardır. Sonuç olarak, eğer bir müşteri bir hizmete istekte bulunmak isterse Service Discovery mekanizmasını kullanmalıdır.
Service Discovery işleri iki bölümde ele alır. İlk olarak, bir örneğin kayıt olması ve var olması için bir mekanizma sağlar. İkincisi, kaydolduktan sonra hizmeti bulmanın bir yolunu sağlar.

# Hibernate, JPA, Spring Data frameworks

![](https://miro.medium.com/v2/resize:fit:720/format:webp/1*NewjznGvudKRZBBftOV7cQ.png)

## Hibernate
Hibernate çıkış amacı bir ORM(Object Relational Mapping) toolu olması. Yani çıkış amacı ***ilişkisel veritabanlarıdır***.
ORM toollarından önce yazılımcılar kendileri objeleri veritabanındaki tablolarla eşleştirecek kodlar yazıyorlardı. Hibernate gibi toollar yazılımcıları bu yükten kurtardı.
ORM Objeleri ilişkisel veritabanında mapping yapmaya yarar. ORM(Object Relational Mapping) bu konseptin adıdır, bir araç değildir. Hibernate bir ORM aracıdır ve EclipseLink, OpenJPA, TopLink gibi birçok farklı ORM aracı bulunmaktadır. Hibernate, Java dünyasındaki popüler ORM araçlarından biridir.
Hibernate, EclipseLink gibi ORM toolları bir JPA implementasyonudur.
Hibernate’ı projemizde kullanmak için ilgili ayarlamaları yapmak gerekmektedir. Bu ayarlamayı hibernate.cfg.xml adında bir dosyada yapıyoruz. Bu ayarlama dosyamızda kullancağımız veritabanının sürücüsü, veritabanı bağlantı bilgileri, Hibernate çalıştığında hangi modda çalışacağı ve model ayarlamaları bu dosyada yapılmaktadır.
Hibernate, JDBC aracılığı ile veritabanı ve uygulama arasında ilişki kurarak, veritabanı üzerinde yapılan CRUD işlemlerini daha hızlı daha kolay ve daha basit bir şekilde yapabilmeyi sağlar.

Örnek: JDBC ile veritabanına kayıt eklerken kullandığımız yapı;
`statement.executeUpdate( "INSERT INTO customer VALUES ('Ipek', 'Cil')");`
Hibernate ile kayıt eklerken kullanacağımız yapı;
`session.save(customer);`

Bazı diğer Hibernate kod örnekleri;

`session.update(customer);`
`session.delete(customer);`
`session.saveOrUpdate(customer);`

Projelerimizde Hibarnate kullanmak için onu Maven veya jar dosyası aracılığı ile ortamımıza eklememiz ardından XML dosyasına gerekli bilgileri girmemiz gerekir.

## JPA (Java Persistence API)
JPA, aynı işi farklı metot ismi-yöntemlerle yapan ORM araçlarının belirli bir standarta sahip olmayışından ve kütüphane karmaşıklığına yol açmasından ötürü ortaya çıkmıştır. 
Java topluluğu, Java ORM kütüphane standartlarını JPA olarak belirlemiştir. Bu sayede kullanılan ORM aracı ister Hibernate ister EclipseLink olsun aynı metot adları aynı sonucu verecektir.
onuç olarak JPA sadece bir standart ortaya koyar ve veri üzerinde kendisi bir işlem yapmaz. Bunun için bu standartları implemente eden ayrı bir araca ihtiyaç vardır, JPA implementasyonu yapan başlıca araçlar arasında Hibernate, TopLink, EclipseLink ve OpenJPA sayılabilir.

## Spring Data Frameworks
Spring Data Frameworks, JPA (Java Persistence API), MongoDB, Redis, Cassandra, Neo4j ve diğer popüler veritabanları için hazır veri erişim katmanları sağlar. Bu veri erişim katmanları, tekrar eden kodu azaltır, geliştirme sürecini hızlandırır ve veritabanı işlemlerini kolaylaştırır.
###Spring Data JPA Framework'ün sağladığı ana özellikler:
1. CRUD Operasyonları
`public interface BookRepository extends JpaRepository<Book, Long> {}`
Yukarıdaki kod parçası, Book sınıfını temsil eden ve ID tipi olarak Long kullanan bir BookRepository arayüzünü tanımlar. JpaRepository arabirimini genişletir, böylece temel CRUD işlemlerini sağlar.
2. Query Methods
Spring Data JPA'nın sağladığı Query Methods özelliği, method isimlendirme kurallarını kullanarak özel sorgular oluşturmayı sağlar. Bu şekilde, sorguları doğrudan Java metodları üzerinden tanımlayabiliriz.

```public interface BookRepository extends JpaRepository<Book, Long> {
    List<Book> findByTitle(String title);
```
`findByTitle(String title)` metodu kullanılarak kitap başlığına göre arama yapılır.findByTitle metodu kullanılarak kitap başlığına göre arama yapılır.

`List<Book> findByAuthorName(String authorName);`  Belirli bir yazarın kitaplarını aramak için.

Spring Data JPA, bu gibi Query Methods'ları analiz ederek otomatik olarak uygun JPQL (Java Persistence Query Language) sorgularını oluşturur ve veritabanında çalıştırır. Bu sayede, sorguları doğrudan Java metodları üzerinden çağırarak, tekrar eden sorgu yazma ihtiyacını ortadan kaldırır ve kodu daha temiz hale getirir.

3. Pagination ve Sorting
`Page<Book> findAll(PageRequest pageRequest);`
```
    PageRequest pageRequest = PageRequest.of(pageNumber, pageSize);
    Page<Book> booksPage = bookRepository.findAll(pageRequest);
    List<Book> books = booksPage.getContent(); 
```

Yukarıdaki kod, belirli bir sayfanın kitaplarını alır. pageNumber değişkeni hangi sayfanın alınacağını belirtir, pageSize ise her sayfada kaç öğe olacağını belirtir.

`List<Book> findAll(Sort sort);`
```
    Sort sort = Sort.by(Sort.Direction.ASC, "title");
    List<Book> sortedBooks = bookRepository.findAll(sort);
```

Yukarıdaki kod, kitapları başlığa göre artan sıralamaya göre alır. Sort.Direction.ASC artan sıralamayı belirtir, "title" ise sıralama yapılacak özelliği belirtir.

###Spring Data JDBC Spring Data JDBC, ilişkisel veritabanlarına doğrudan JDBC (Java Database Connectivity) kullanarak erişmek için bir araçtır.

# Docker ile RabbitMQ Kurulumu
![Docker-RabbitMQ](https://raw.githubusercontent.com/ipekcill/image-resources/main/Docker-RabbitMQ.png)

# Docker ile MySQL Kurulumu
![Docker-MySQL](https://raw.githubusercontent.com/ipekcill/image-resources/main/Docker-MySQL.png)


![Database-install-run](https://raw.githubusercontent.com/ipekcill/image-resources/main/Database%20install-run.png)

# Docker komutları örnekleri
- docker run : Container oluşturma ve çalıştırma
  `docker container run --name ipek-mysql --detach --publish 3306:3306 -e MYSQL_ROOT_PASSWORD=secret mysql`  : Bir MySQL containerı başlatır ve çalıştırır.
--name ipek-mysql: Oluşturulan konteynere "ipek-mysql" adını verir.
--detach veya -d: Konteyneri arka planda çalıştırır.
--publish 3306:3306: Host makinenin 3306 numaralı portunu konteynerin 3306 numaralı portuna yönlendirir. Bu, MySQL sunucusuna dışarıdan erişmek için kullanılır.
-e MYSQL_ROOT_PASSWORD=secret: MySQL root kullanıcısının şifresini "secret" olarak belirler. -e bayrağı, bir ortam değişkeni belirtir.
mysql: Kullanılan Docker imajıdır. Bu komut, Docker Hub'dan resmi MySQL imajını kullanır.
Yani, bu komut, "ipek-mysql" adında bir MySQL konteyneri başlatır, bu konteynerin dış 3306 portunu iç 3306 porta yönlendirir, MySQL root kullanıcısının şifresini "secret" olarak belirler ve arka planda çalıştırır.
- docker pull : Belirli bir imajı indirme
- docker push : Belirli bir imajı gönderme
- docker search : İmajları bulma
- docker rm  Container'ları kaldırma
- docker run --rm : Durdurduktan sonra containerı silme
- docker rm -v : Container ile ilşkili birimleri kaldırma









